//,temp,sample_4074.java,2,12,temp,sample_5014.java,2,12
//,3
public class xxx {
private static void cleanup(MiniDFSCluster cluster) {
if (cluster != null) {
try {
cluster.shutdown();
} catch (Exception e) {


log.info("could not shutdown minidfscluster");
}
}
}

};