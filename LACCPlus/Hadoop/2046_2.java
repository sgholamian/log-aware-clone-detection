//,temp,TestNameNodeRespectsBindHostKeys.java,218,262,temp,TestNameNodeRespectsBindHostKeys.java,151,191
//,3
public class xxx {
  @Test(timeout=300000)
  public void testHttpBindHostKey() throws IOException {
    Configuration conf = new HdfsConfiguration();
    MiniDFSCluster cluster = null;

    LOG.info("Testing without " + DFS_NAMENODE_HTTP_BIND_HOST_KEY);

    // NN should not bind the wildcard address by default.
    try {
      conf.set(DFS_NAMENODE_HTTP_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(0).build();
      cluster.waitActive();
      String address = cluster.getNameNode().getHttpAddress().toString();
      assertFalse("HTTP Bind address not expected to be wildcard by default.",
                  address.startsWith(WILDCARD_ADDRESS));
    } finally {
      if (cluster != null) {
        cluster.shutdown();
        cluster = null;
      }
    }

    LOG.info("Testing with " + DFS_NAMENODE_HTTP_BIND_HOST_KEY);

    // Tell NN to bind the wildcard address.
    conf.set(DFS_NAMENODE_HTTP_BIND_HOST_KEY, WILDCARD_ADDRESS);

    // Verify that NN binds wildcard address now.
    try {
      conf.set(DFS_NAMENODE_HTTP_ADDRESS_KEY, LOCALHOST_SERVER_ADDRESS);
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(0).build();
      cluster.waitActive();
      String address = cluster.getNameNode().getHttpAddress().toString();
      assertTrue("HTTP Bind address " + address + " is not wildcard.",
                 address.startsWith(WILDCARD_ADDRESS));
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }

};