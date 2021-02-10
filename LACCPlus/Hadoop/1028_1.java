//,temp,TestNameNodeRespectsBindHostKeys.java,109,149,temp,TestNameNodeRespectsBindHostKeys.java,69,107
//,3
public class xxx {
  @Test (timeout=300000)
  public void testServiceRpcBindHostKey() throws IOException {
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = null;

    LOG.info("Testing without " + DFS_NAMENODE_SERVICE_RPC_BIND_HOST_KEY);
    
    conf.set(DFS_NAMENODE_SERVICE_RPC_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);

    // NN should not bind the wildcard address by default.
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(0).build();
      cluster.waitActive();
      String address = getServiceRpcServerAddress(cluster);
      assertThat("Bind address not expected to be wildcard by default.",
                 address, not("/" + WILDCARD_ADDRESS));
    } finally {
      if (cluster != null) {
        cluster.shutdown();
        cluster = null;
      }
    }

    LOG.info("Testing with " + DFS_NAMENODE_SERVICE_RPC_BIND_HOST_KEY);

    // Tell NN to bind the wildcard address.
    conf.set(DFS_NAMENODE_SERVICE_RPC_BIND_HOST_KEY, WILDCARD_ADDRESS);

    // Verify that NN binds wildcard address now.
    try {
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(0).build();
      cluster.waitActive();
      String address = getServiceRpcServerAddress(cluster);
      assertThat("Bind address " + address + " is not wildcard.",
                 address, is("/" + WILDCARD_ADDRESS));
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};