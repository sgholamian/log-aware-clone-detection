//,temp,sample_4490.java,2,12,temp,sample_4547.java,2,12
//,2
public class xxx {
public void checkAccess(Account caller, Domain domain) throws PermissionDeniedException {
for (SecurityChecker checker : _securityCheckers) {
if (checker.checkAccess(caller, domain)) {
if (s_logger.isDebugEnabled()) {


log.info("access granted to to by");
}
}
}
}

};