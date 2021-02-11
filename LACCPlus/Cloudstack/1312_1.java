//,temp,UserVmManagerImpl.java,813,868,temp,UserVmManagerImpl.java,702,763
//,3
public class xxx {
    private boolean resetVMSSHKeyInternal(Long vmId, String sshPublicKey, String password) throws ResourceUnavailableException, InsufficientCapacityException {
        Long userId = CallContext.current().getCallingUserId();
        VMInstanceVO vmInstance = _vmDao.findById(vmId);

        VMTemplateVO template = _templateDao.findByIdIncludingRemoved(vmInstance.getTemplateId());
        Nic defaultNic = _networkModel.getDefaultNic(vmId);
        if (defaultNic == null) {
            s_logger.error("Unable to reset SSH Key for vm " + vmInstance + " as the instance doesn't have default nic");
            return false;
        }

        Network defaultNetwork = _networkDao.findById(defaultNic.getNetworkId());
        NicProfile defaultNicProfile = new NicProfile(defaultNic, defaultNetwork, null, null, null, _networkModel.isSecurityGroupSupportedInNetwork(defaultNetwork),
                _networkModel.getNetworkTag(template.getHypervisorType(), defaultNetwork));

        VirtualMachineProfile vmProfile = new VirtualMachineProfileImpl(vmInstance);

        if (template.isEnablePassword()) {
            vmProfile.setParameter(VirtualMachineProfile.Param.VmPassword, password);
        }

        UserDataServiceProvider element = _networkMgr.getSSHKeyResetProvider(defaultNetwork);
        if (element == null) {
            throw new CloudRuntimeException("Can't find network element for " + Service.UserData.getName() + " provider needed for SSH Key reset");
        }
        boolean result = element.saveSSHKey(defaultNetwork, defaultNicProfile, vmProfile, sshPublicKey);

        // Need to reboot the virtual machine so that the password gets redownloaded from the DomR, and reset on the VM
        if (!result) {
            s_logger.debug("Failed to reset SSH Key for the virtual machine; no need to reboot the vm");
            return false;
        } else {
            final UserVmVO userVm = _vmDao.findById(vmId);
            _vmDao.loadDetails(userVm);
            userVm.setDetail("SSH.PublicKey", sshPublicKey);
            if (template.isEnablePassword()) {
                userVm.setPassword(password);
                //update the encrypted password in vm_details table too
                encryptAndStorePassword(userVm, password);
            } else {
                _vmDao.saveDetails(userVm);
            }

            if (vmInstance.getState() == State.Stopped) {
                s_logger.debug("Vm " + vmInstance + " is stopped, not rebooting it as a part of SSH Key reset");
                return true;
            }
            if (rebootVirtualMachine(userId, vmId) == null) {
                s_logger.warn("Failed to reboot the vm " + vmInstance);
                return false;
            } else {
                s_logger.debug("Vm " + vmInstance + " is rebooted successfully as a part of SSH Key reset");
                return true;
            }
        }
    }

};