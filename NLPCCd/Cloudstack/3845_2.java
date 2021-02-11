//,temp,sample_4710.java,2,12,temp,sample_4711.java,2,12
//,2
public class xxx {
public void checkZoneAccess(final Account caller, final DataCenter zone) {
for (final SecurityChecker checker : _secChecker) {
if (checker.checkAccess(caller, zone)) {
if (s_logger.isDebugEnabled()) {


log.info("access granted to to zone by");
}
}
}
}

};