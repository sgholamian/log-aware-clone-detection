//,temp,sample_5152.java,2,10,temp,sample_3758.java,2,10
//,2
public class xxx {
public static void tearDown() throws Exception {
try {
UTIL.shutdownMiniCluster();
} catch (Exception e) {


log.info("failure shutting down cluster");
}
}

};