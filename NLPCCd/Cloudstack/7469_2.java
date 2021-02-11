//,temp,sample_4490.java,2,12,temp,sample_4547.java,2,12
//,2
public class xxx {
public void checkAccess(Account account, ServiceOffering so) throws PermissionDeniedException {
for (SecurityChecker checker : _securityCheckers) {
if (checker.checkAccess(account, so)) {
if (s_logger.isDebugEnabled()) {


log.info("access granted to to by");
}
}
}
}

};