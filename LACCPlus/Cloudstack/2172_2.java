//,temp,UserVmManagerImpl.java,3075,3183,temp,UserVmManagerImpl.java,3018,3073
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VM_CREATE, eventDescription = "deploying Vm", create = true)
    public UserVm createBasicSecurityGroupVirtualMachine(DataCenter zone, ServiceOffering serviceOffering, VirtualMachineTemplate template, List<Long> securityGroupIdList,
            Account owner, String hostName, String displayName, Long diskOfferingId, Long diskSize, String group, HypervisorType hypervisor, HTTPMethod httpmethod,
            String userData, String sshKeyPair, Map<Long, IpAddresses> requestedIps, IpAddresses defaultIps, Boolean displayVm, String keyboard, List<Long> affinityGroupIdList,
            Map<String, String> customParametes, String customId, Map<String, Map<Integer, String>> dhcpOptionMap, Map<Long, DiskOffering> dataDiskTemplateToDiskOfferingMap) throws InsufficientCapacityException, ConcurrentOperationException, ResourceUnavailableException,
    StorageUnavailableException, ResourceAllocationException {

        Account caller = CallContext.current().getCallingAccount();
        List<NetworkVO> networkList = new ArrayList<NetworkVO>();

        // Verify that caller can perform actions in behalf of vm owner
        _accountMgr.checkAccess(caller, null, true, owner);

        // Verify that owner can use the service offering
        _accountMgr.checkAccess(owner, serviceOffering);
        _accountMgr.checkAccess(owner, _diskOfferingDao.findById(diskOfferingId));

        // Get default guest network in Basic zone
        Network defaultNetwork = _networkModel.getExclusiveGuestNetwork(zone.getId());

        if (defaultNetwork == null) {
            throw new InvalidParameterValueException("Unable to find a default network to start a vm");
        } else {
            networkList.add(_networkDao.findById(defaultNetwork.getId()));
        }

        boolean isVmWare = (template.getHypervisorType() == HypervisorType.VMware || (hypervisor != null && hypervisor == HypervisorType.VMware));

        if (securityGroupIdList != null && isVmWare) {
            throw new InvalidParameterValueException("Security group feature is not supported for vmWare hypervisor");
        } else if (!isVmWare && _networkModel.isSecurityGroupSupportedInNetwork(defaultNetwork) && _networkModel.canAddDefaultSecurityGroup()) {
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
                userData, sshKeyPair, hypervisor, caller, requestedIps, defaultIps, displayVm, keyboard, affinityGroupIdList, customParametes, customId, dhcpOptionMap, dataDiskTemplateToDiskOfferingMap);

    }

};