//,temp,sample_9539.java,2,11,temp,sample_9531.java,2,11
//,3
public class xxx {
public VirtualMachine migrateVirtualMachine(Long vmId, Host destinationHost) throws ResourceUnavailableException, ConcurrentOperationException, ManagementServerException, VirtualMachineMigrationException {
Account caller = CallContext.current().getCallingAccount();
if (!_accountMgr.isRootAdmin(caller.getId())) {
if (s_logger.isDebugEnabled()) {


log.info("caller is not a root admin permission denied to migrate the vm");
}
}
}

};