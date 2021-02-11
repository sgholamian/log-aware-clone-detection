//,temp,ConfigurationManagerImpl.java,3908,3985,temp,ConfigurationManagerImpl.java,3684,3777
//,3
public class xxx {
    @DB
    public boolean releasePublicIpRange(final long vlanDbId, final long userId, final Account caller) {
        VlanVO vlan = _vlanDao.findById(vlanDbId);
        if(vlan == null) {
            s_logger.warn("VLAN information for Account '" + caller + "', User '" + userId + "' VLAN '" + vlanDbId + "' is null. This is NPE situation.");
        }

        // Verify range is dedicated
        boolean isAccountSpecific = false;
        final List<AccountVlanMapVO> acctVln = _accountVlanMapDao.listAccountVlanMapsByVlan(vlanDbId);
        // Verify range is dedicated
        if (acctVln != null && !acctVln.isEmpty()) {
            isAccountSpecific = true;
        }

        boolean isDomainSpecific = false;
        final List<DomainVlanMapVO> domainVlan = _domainVlanMapDao.listDomainVlanMapsByVlan(vlanDbId);
        // Check for domain wide pool. It will have an entry for domain_vlan_map.
        if (domainVlan != null && !domainVlan.isEmpty()) {
            isDomainSpecific = true;
        }

        if (!isAccountSpecific && !isDomainSpecific) {
            throw new InvalidParameterValueException("Can't release Public IP range " + vlanDbId
                    + " as it not dedicated to any domain and any account");
        }
        // Check if range has any allocated public IPs
        final long allocIpCount = _publicIpAddressDao.countIPs(vlan.getDataCenterId(), vlanDbId, true);
        final List<IPAddressVO> ips = _publicIpAddressDao.listByVlanId(vlanDbId);
        boolean success = true;
        final List<IPAddressVO> ipsInUse = new ArrayList<IPAddressVO>();
        if (allocIpCount > 0) {
            try {
                vlan = _vlanDao.acquireInLockTable(vlanDbId, 30);
                if (vlan == null) {
                    throw new CloudRuntimeException("Unable to acquire vlan configuration: " + vlanDbId);
                }
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("lock vlan " + vlanDbId + " is acquired");
                }
                for (final IPAddressVO ip : ips) {
                    // Disassociate allocated IP's that are not in use
                    if (!ip.isOneToOneNat() && !ip.isSourceNat() && !(_firewallDao.countRulesByIpId(ip.getId()) > 0)) {
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("Releasing Public IP addresses" + ip + " of vlan " + vlanDbId + " as part of Public IP" + " range release to the system pool");
                        }
                        success = success && _ipAddrMgr.disassociatePublicIpAddress(ip.getId(), userId, caller);
                    } else {
                        ipsInUse.add(ip);
                    }
                }
                if (!success) {
                    s_logger.warn("Some Public IP addresses that were not in use failed to be released as a part of" + " vlan " + vlanDbId + "release to the system pool");
                }
            } finally {
                _vlanDao.releaseFromLockTable(vlanDbId);
            }
        }

        // A Public IP range can only be dedicated to one account at a time
        if (isAccountSpecific && _accountVlanMapDao.remove(acctVln.get(0).getId())) {
            // generate usage events to remove dedication for every ip in the range that has been disassociated
            for (final IPAddressVO ip : ips) {
                if (!ipsInUse.contains(ip)) {
                    UsageEventUtils.publishUsageEvent(EventTypes.EVENT_NET_IP_RELEASE, acctVln.get(0).getAccountId(), ip.getDataCenterId(), ip.getId(), ip.getAddress().toString(),
                            ip.isSourceNat(), vlan.getVlanType().toString(), ip.getSystem(), ip.getClass().getName(), ip.getUuid());
                }
            }
            // decrement resource count for dedicated public ip's
            _resourceLimitMgr.decrementResourceCount(acctVln.get(0).getAccountId(), ResourceType.public_ip, new Long(ips.size()));
            return true;
        } else if (isDomainSpecific && _domainVlanMapDao.remove(domainVlan.get(0).getId())) {
            s_logger.debug("Remove the vlan from domain_vlan_map successfully.");
            return true;
        } else {
            return false;
        }
    }

};