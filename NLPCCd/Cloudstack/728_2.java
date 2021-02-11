//,temp,sample_3599.java,2,9,temp,sample_3603.java,2,10
//,3
public class xxx {
public static boolean releaseGlobalLock(String name) {
try (Connection conn = getConnectionForGlobalLocks(name, false);) {
if (conn == null) {


log.info("unable to acquire db connection for global lock system");
}
}
}

};