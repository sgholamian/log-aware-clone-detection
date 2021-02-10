//,temp,TestDataNodeTcpNoDelay.java,100,128,temp,TestDataNodeTcpNoDelay.java,72,98
//,3
public class xxx {
  @Test
  public void testTcpNoDelayEnabled() throws Exception {
    Configuration testConf = new Configuration(baseConf);
    // here we do not have to config TCP_NDELAY settings, since they should be
    // active by default
    testConf.set(HADOOP_RPC_SOCKET_FACTORY_CLASS_DEFAULT_KEY,
        SocketFactoryWrapper.class.getName());

    SocketFactory defaultFactory = NetUtils.getDefaultSocketFactory(testConf);
    LOG.info("Socket factory is " + defaultFactory.getClass().getName());
    MiniDFSCluster dfsCluster =
        new MiniDFSCluster.Builder(testConf).numDataNodes(3).build();
    dfsCluster.waitActive();

    DistributedFileSystem dfs = dfsCluster.getFileSystem();

    try {
      createData(dfs);
      transferBlock(dfs);

      // check that TCP_NODELAY has been set on all sockets
      assertTrue(SocketFactoryWrapper.wasTcpNoDelayActive());
    } finally {
      SocketFactoryWrapper.reset();
      dfsCluster.shutdown();
    }
  }

};