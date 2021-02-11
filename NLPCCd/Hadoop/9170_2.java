//,temp,sample_8333.java,2,11,temp,sample_614.java,2,11
//,3
public class xxx {
private void assertCanNotStartNamenode(MiniDFSCluster cluster, int nnIndex) {
try {
cluster.restartNameNode(nnIndex, false);
fail("Should not have been able to start NN" + (nnIndex) + " without shared dir");
} catch (IOException ioe) {


log.info("got expected exception");
}
}

};