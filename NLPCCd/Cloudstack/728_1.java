//,temp,sample_3599.java,2,9,temp,sample_3603.java,2,10
//,3
public class xxx {
public static boolean getGlobalLock(String name, int timeoutSeconds) {
Connection conn = getConnectionForGlobalLocks(name, true);
if (conn == null) {


log.info("unable to acquire db connection for global lock system");
}
}

};