//,temp,UserVmManagerImpl.java,765,811,temp,UserVmManagerImpl.java,660,700
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_VM_RESETSSHKEY, eventDescription = "resetting Vm SSHKey", async = true)
    public UserVm resetVMSSHKey(ResetVMSSHKeyCmd cmd) throws ResourceUnavailableException, InsufficientCapacityException {

        Account caller = CallContext.current().getCallingAccount();
        Account owner = _accountMgr.finalizeOwner(caller, cmd.getAccountName(), cmd.getDomainId(), cmd.getProjectId());
        Long vmId = cmd.getId();

        UserVmVO userVm = _vmDao.findById(cmd.getId());
        if (userVm == null) {
            throw new InvalidParameterValueException("unable to find a virtual machine by id" + cmd.getId());
        }

        _vmDao.loadDetails(userVm);
        VMTemplateVO template = _templateDao.findByIdIncludingRemoved(userVm.getTemplateId());

        // Do parameters input validation

        if (userVm.getState() == State.Error || userVm.getState() == State.Expunging) {
            s_logger.error("vm is not in the right state: " + vmId);
            throw new InvalidParameterValueException("Vm with specified id is not in the right state");
        }
        if (userVm.getState() != State.Stopped) {
            s_logger.error("vm is not in the right state: " + vmId);
            throw new InvalidParameterValueException("Vm " + userVm + " should be stopped to do SSH Key reset");
        }

        SSHKeyPairVO s = _sshKeyPairDao.findByName(owner.getAccountId(), owner.getDomainId(), cmd.getName());
        if (s == null) {
            throw new InvalidParameterValueException("A key pair with name '" + cmd.getName() + "' does not exist for account " + owner.getAccountName()
            + " in specified domain id");
        }

        _accountMgr.checkAccess(caller, null, true, userVm);
        String password = null;
        String sshPublicKey = s.getPublicKey();
        if (template != null && template.isEnablePassword()) {
            password = _mgr.generateRandomPassword();
        }

        boolean result = resetVMSSHKeyInternal(vmId, sshPublicKey, password);

        if (!result) {
            throw new CloudRuntimeException("Failed to reset SSH Key for the virtual machine ");
        }
        return userVm;
    }

};