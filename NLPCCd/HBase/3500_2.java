//,temp,sample_4901.java,2,10,temp,sample_3116.java,2,10
//,2
public class xxx {
public static void cleanupTest() throws Exception {
try {
UTIL.shutdownMiniCluster();
} catch (Exception e) {


log.info("failure shutting down cluster");
}
}

};