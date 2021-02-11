//,temp,TestBookKeeperConfiguration.java,84,103,temp,TestCurrentInprogress.java,74,90
//,3
public class xxx {
  @BeforeClass
  public static void setupZooKeeper() throws Exception {
    // create a ZooKeeper server(dataDir, dataLogDir, port)
    LOG.info("Starting ZK server");
    ZkTmpDir = File.createTempFile("zookeeper", "test");
    ZkTmpDir.delete();
    ZkTmpDir.mkdir();

    try {
      zks = new ZooKeeperServer(ZkTmpDir, ZkTmpDir, ZooKeeperDefaultPort);
      serverFactory = new NIOServerCnxnFactory();
      serverFactory.configure(new InetSocketAddress(ZooKeeperDefaultPort), 10);
      serverFactory.startup(zks);
    } catch (Exception e) {
      LOG.error("Exception while instantiating ZooKeeper", e);
    }

    boolean b = LocalBookKeeper.waitForServerUp(HOSTPORT, CONNECTION_TIMEOUT);
    LOG.debug("ZooKeeper server up: " + b);
  }

};