//,temp,TestBookKeeperConfiguration.java,84,103,temp,TestCurrentInprogress.java,74,90
//,3
public class xxx {
  @BeforeClass
  public static void setupZooKeeper() throws Exception {
    LOG.info("Starting ZK server");
    zkTmpDir = File.createTempFile("zookeeper", "test");
    zkTmpDir.delete();
    zkTmpDir.mkdir();
    try {
      zks = new ZooKeeperServer(zkTmpDir, zkTmpDir, ZooKeeperDefaultPort);
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