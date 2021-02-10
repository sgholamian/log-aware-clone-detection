//,temp,TestHDFSServerPorts.java,364,394,temp,TestHDFSServerPorts.java,335,359
//,3
public class xxx {
  @Test(timeout = 300000)
  public void testSecondaryNodePorts() throws Exception {
    NameNode nn = null;
    try {
      nn = startNameNode();

      // bind http server to the same port as name-node
      Configuration conf2 = new HdfsConfiguration(config);
      conf2.set(DFSConfigKeys.DFS_NAMENODE_SECONDARY_HTTP_ADDRESS_KEY, 
                config.get(DFSConfigKeys.DFS_NAMENODE_HTTP_ADDRESS_KEY));
      LOG.info("= Starting 1 on: " + 
                                 conf2.get(DFSConfigKeys.DFS_NAMENODE_SECONDARY_HTTP_ADDRESS_KEY));
      boolean started = canStartSecondaryNode(conf2);
      assertFalse(started); // should fail

      // bind http server to a different port
      conf2.set(DFSConfigKeys.DFS_NAMENODE_SECONDARY_HTTP_ADDRESS_KEY, THIS_HOST);
      LOG.info("= Starting 2 on: " + 
                                 conf2.get(DFSConfigKeys.DFS_NAMENODE_SECONDARY_HTTP_ADDRESS_KEY));
      started = canStartSecondaryNode(conf2);
      assertTrue(started); // should start now
    } finally {
      stopNameNode(nn);
    }
  }

};