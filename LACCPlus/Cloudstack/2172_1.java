//,temp,UserVmManagerImpl.java,3075,3183,temp,UserVmManagerImpl.java,3018,3073
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VM_CREATE, eventDescription = "deploying Vm", create = true)
    public UserVm createAdvancedSecurityGroupVirtualMachine(DataCenter zone, ServiceOffering serviceOffering, VirtualMachineTemplate template, List<Long> networkIdList,
            List<Long> securityGroupIdList, Account owner, String hostName, String displayName, Long diskOfferingId, Long diskSize, String group, HypervisorType hypervisor,
            HTTPMethod httpmethod, String userData, String sshKeyPair, Map<Long, IpAddresses> requestedIps, IpAddresses defaultIps, Boolean displayVm, String keyboard,
            List<Long> affinityGroupIdList, Map<String, String> customParameters, String customId, Map<String, Map<Integer, String>> dhcpOptionMap, Map<Long, DiskOffering> dataDiskTemplateToDiskOfferingMap) throws InsufficientCapacityException, ConcurrentOperationException,
    ResourceUnavailableException, StorageUnavailableException, ResourceAllocationException {

        Account caller = CallContext.current().getCallingAccount();
        List<NetworkVO> networkList = new ArrayList<NetworkVO>();
        boolean isSecurityGroupEnabledNetworkUsed = false;
        boolean isVmWare = (template.getHypervisorType() == HypervisorType.VMware || (hypervisor != null && hypervisor == HypervisorType.VMware));

        // Verify that caller can perform actions in behalf of vm owner
        _accountMgr.checkAccess(caller, null, true, owner);

        // Verify that owner can use the service offering
        _accountMgr.checkAccess(owner, serviceOffering);
        _accountMgr.checkAccess(owner, _diskOfferingDao.findById(diskOfferingId));

        // If no network is specified, find system security group enabled network
        if (networkIdList == null || networkIdList.isEmpty()) {
            Network networkWithSecurityGroup = _networkModel.getNetworkWithSGWithFreeIPs(zone.getId());
            if (networkWithSecurityGroup == null) {
                throw new InvalidParameterValueException("No network with security enabled is found in zone id=" + zone.getUuid());
            }

            networkList.add(_networkDao.findById(networkWithSecurityGroup.getId()));
            isSecurityGroupEnabledNetworkUsed = true;

        } else if (securityGroupIdList != null && !securityGroupIdList.isEmpty()) {
            if (isVmWare) {
                throw new InvalidParameterValueException("Security group feature is not supported for vmWare hypervisor");
            }
            // Only one network can be specified, and it should be security group enabled
            if (networkIdList.size() > 1) {
                throw new InvalidParameterValueException("Only support one network per VM if security group enabled");
            }

            NetworkVO network = _networkDao.findById(networkIdList.get(0));

            if (network == null) {
                throw new InvalidParameterValueException("Unable to find network by id " + networkIdList.get(0).longValue());
            }

            if (!_networkModel.isSecurityGroupSupportedInNetwork(network)) {
                throw new InvalidParameterValueException("Network is not security group enabled: " + network.getId());
            }

            networkList.add(network);
            isSecurityGroupEnabledNetworkUsed = true;

        } else {
            // Verify that all the networks are Shared/Guest; can't create combination of SG enabled and disabled networks
            for (Long networkId : networkIdList) {
                NetworkVO network = _networkDao.findById(networkId);

                if (network == null) {
                    throw new InvalidParameterValueException("Unable to find network by id " + networkIdList.get(0).longValue());
                }

                boolean isSecurityGroupEnabled = _networkModel.isSecurityGroupSupportedInNetwork(network);
                if (isSecurityGroupEnabled) {
                    if (networkIdList.size() > 1) {
                        throw new InvalidParameterValueException("Can't create a vm with multiple networks one of" + " which is Security Group enabled");
                    }

                    isSecurityGroupEnabledNetworkUsed = true;
                }

                if (!(network.getTrafficType() == TrafficType.Guest && network.getGuestType() == Network.GuestType.Shared)) {
                    throw new InvalidParameterValueException("Can specify only Shared Guest networks when" + " deploy vm in Advance Security Group enabled zone");
                }

                // Perform account permission check
                if (network.getAclType() == ACLType.Account) {
                    _accountMgr.checkAccess(caller, AccessType.UseEntry, false, network);
                }
                networkList.add(network);
            }
        }

        // if network is security group enabled, and no security group is specified, then add the default security group automatically
        if (isSecurityGroupEnabledNetworkUsed && !isVmWare && _networkModel.canAddDefaultSecurityGroup()) {

            //add the default securityGroup only if no security group is specified
            if (securityGroupIdList == null || securityGroupIdList.isEmpty()) {
                if (securityGroupIdList == null) {
                    securityGroupIdList = new ArrayList<Long>();
                }

                SecurityGroup defaultGroup = _securityGroupMgr.getDefaultSecurityGroup(owner.getId());
                if (defaultGroup != null) {
                    securityGroupIdList.add(defaultGroup.getId());
                } else {
                    // create default security group for the account
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Couldn't find default security group for the account " + owner + " so creating a new one");
                    }
                    defaultGroup = _securityGroupMgr.createSecurityGroup(SecurityGroupManager.DEFAULT_GROUP_NAME, SecurityGroupManager.DEFAULT_GROUP_DESCRIPTION,
                            owner.getDomainId(), owner.getId(), owner.getAccountName());
                    securityGroupIdList.add(defaultGroup.getId());
                }
            }
        }

        return createVirtualMachine(zone, serviceOffering, template, hostName, displayName, owner, diskOfferingId, diskSize, networkList, securityGroupIdList, group, httpmethod,
                userData, sshKeyPair, hypervisor, caller, requestedIps, defaultIps, displayVm, keyboard, affinityGroupIdList, customParameters, customId, dhcpOptionMap, dataDiskTemplateToDiskOfferingMap);
    }

};