//,temp,sample_4710.java,2,12,temp,sample_4711.java,2,12
//,2
public class xxx {
public void checkDiskOfferingAccess(final Account caller, final DiskOffering dof) {
for (final SecurityChecker checker : _secChecker) {
if (checker.checkAccess(caller, dof)) {
if (s_logger.isDebugEnabled()) {


log.info("access granted to to disk offering by");
}
}
}
}

};