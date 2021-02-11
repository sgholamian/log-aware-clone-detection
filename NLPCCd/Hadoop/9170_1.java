//,temp,sample_8333.java,2,11,temp,sample_614.java,2,11
//,3
public class xxx {
private void ensureClusterRestartFails(MiniDFSCluster cluster) {
try {
cluster.restartNameNode();
fail("Cluster should not have successfully started");
} catch (Exception expected) {


log.info("expected exception thrown");
}
}

};