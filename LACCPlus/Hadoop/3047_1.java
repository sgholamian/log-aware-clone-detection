//,temp,TestDataNodeTcpNoDelay.java,100,128,temp,TestDataNodeTcpNoDelay.java,72,98
//,3
public class xxx {
  @Test
  public void testTcpNoDelayDisabled() throws Exception {
    Configuration testConf = new Configuration(baseConf);
    // disable TCP_NODELAY in settings
    setTcpNoDelay(testConf, false);
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

      // we can only check that TCP_NODELAY was disabled on some sockets,
      // since part of the client write path always enables TCP_NODELAY
      // by necessity
      assertFalse(SocketFactoryWrapper.wasTcpNoDelayActive());
    } finally {
      SocketFactoryWrapper.reset();
      dfsCluster.shutdown();
    }
  }

};