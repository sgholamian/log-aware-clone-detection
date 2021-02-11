//,temp,sample_4548.java,2,12,temp,sample_4547.java,2,12
//,2
public class xxx {
public void checkAccess(Account account, DiskOffering dof) throws PermissionDeniedException {
for (SecurityChecker checker : _securityCheckers) {
if (checker.checkAccess(account, dof)) {
if (s_logger.isDebugEnabled()) {


log.info("access granted to to by");
}
}
}
}

};