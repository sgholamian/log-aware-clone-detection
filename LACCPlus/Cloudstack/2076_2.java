//,temp,ConfigurationManagerImpl.java,3908,3985,temp,ConfigurationManagerImpl.java,3684,3777
//,3
public class xxx {
    @Override
    @DB
    public boolean deleteVlanAndPublicIpRange(final long userId, final long vlanDbId, final Account caller) {
        VlanVO vlanRange = _vlanDao.findById(vlanDbId);
        if (vlanRange == null) {
            throw new InvalidParameterValueException("Please specify a valid IP range id.");
        }

        boolean isAccountSpecific = false;
        final List<AccountVlanMapVO> acctVln = _accountVlanMapDao.listAccountVlanMapsByVlan(vlanRange.getId());
        // Check for account wide pool. It will have an entry for
        // account_vlan_map.
        if (acctVln != null && !acctVln.isEmpty()) {
            isAccountSpecific = true;
        }

        boolean isDomainSpecific = false;
        List<DomainVlanMapVO> domainVlan = _domainVlanMapDao.listDomainVlanMapsByVlan(vlanRange.getId());
        // Check for domain wide pool. It will have an entry for domain_vlan_map.
        if (domainVlan != null && !domainVlan.isEmpty()) {
            isDomainSpecific = true;
        }

        // Check if the VLAN has any allocated public IPs
        final List<IPAddressVO> ips = _publicIpAddressDao.listByVlanId(vlanDbId);
        if (isAccountSpecific) {
            int resourceCountToBeDecrement = 0;
            try {
                vlanRange = _vlanDao.acquireInLockTable(vlanDbId, 30);
                if (vlanRange == null) {
                    throw new CloudRuntimeException("Unable to acquire vlan configuration: " + vlanDbId);
                }

                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("lock vlan " + vlanDbId + " is acquired");
                }
                for (final IPAddressVO ip : ips) {
                    boolean success = true;
                    if (ip.isOneToOneNat()) {
                        throw new InvalidParameterValueException("Can't delete account specific vlan " + vlanDbId + " as ip " + ip
                                + " belonging to the range is used for static nat purposes. Cleanup the rules first");
                    }

                    if (ip.isSourceNat()) {
                        throw new InvalidParameterValueException("Can't delete account specific vlan " + vlanDbId + " as ip " + ip
                                + " belonging to the range is a source nat ip for the network id=" + ip.getSourceNetworkId()
                                + ". IP range with the source nat ip address can be removed either as a part of Network, or account removal");
                    }

                    if (_firewallDao.countRulesByIpId(ip.getId()) > 0) {
                        throw new InvalidParameterValueException("Can't delete account specific vlan " + vlanDbId + " as ip " + ip
                                + " belonging to the range has firewall rules applied. Cleanup the rules first");
                    }
                    if (ip.getAllocatedTime() != null) {// This means IP is allocated
                        // release public ip address here
                        success = _ipAddrMgr.disassociatePublicIpAddress(ip.getId(), userId, caller);
                    }
                    if (!success) {
                        s_logger.warn("Some ip addresses failed to be released as a part of vlan " + vlanDbId + " removal");
                    } else {
                        resourceCountToBeDecrement++;
                        UsageEventUtils.publishUsageEvent(EventTypes.EVENT_NET_IP_RELEASE, acctVln.get(0).getAccountId(), ip.getDataCenterId(), ip.getId(),
                                ip.getAddress().toString(), ip.isSourceNat(), vlanRange.getVlanType().toString(), ip.getSystem(), ip.getClass().getName(), ip.getUuid());
                    }
                }
            } finally {
                _vlanDao.releaseFromLockTable(vlanDbId);
                if (resourceCountToBeDecrement > 0) {  //Making sure to decrement the count of only success operations above. For any reaason if disassociation fails then this number will vary from original range length.
                    _resourceLimitMgr.decrementResourceCount(acctVln.get(0).getAccountId(), ResourceType.public_ip, new Long(resourceCountToBeDecrement));
                }
            }
        } else {   // !isAccountSpecific
            final NicIpAliasVO ipAlias = _nicIpAliasDao.findByGatewayAndNetworkIdAndState(vlanRange.getVlanGateway(), vlanRange.getNetworkId(), NicIpAlias.State.active);
            //check if the ipalias belongs to the vlan range being deleted.
            if (ipAlias != null && vlanDbId == _publicIpAddressDao.findByIpAndSourceNetworkId(vlanRange.getNetworkId(), ipAlias.getIp4Address()).getVlanId()) {
                throw new InvalidParameterValueException("Cannot delete vlan range " + vlanDbId + " as " + ipAlias.getIp4Address()
                        + "is being used for providing dhcp service in this subnet. Delete all VMs in this subnet and try again");
            }
            final long allocIpCount = _publicIpAddressDao.countIPs(vlanRange.getDataCenterId(), vlanDbId, true);
            if (allocIpCount > 0) {
                throw new InvalidParameterValueException(allocIpCount + "  Ips are in use. Cannot delete this vlan");
            }
        }

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(final TransactionStatus status) {
                _publicIpAddressDao.deletePublicIPRange(vlanDbId);
                _vlanDao.remove(vlanDbId);
            }
        });

        return true;
    }

};