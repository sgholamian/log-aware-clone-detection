//,temp,sample_4878.java,2,10,temp,sample_4061.java,2,12
//,3
public class xxx {
public static void cleanupTest() throws Exception {
try {
UTIL.shutdownMiniCluster();
} catch (Exception e) {


log.info("failure shutting down cluster");
}
}

};