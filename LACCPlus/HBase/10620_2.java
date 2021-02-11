//,temp,TestSpnegoHttpServer.java,144,166,temp,TestThriftSpnegoHttpServer.java,100,122
//,3
public class xxx {
  private static SimpleKdcServer buildMiniKdc() throws Exception {
    SimpleKdcServer kdc = new SimpleKdcServer();

    final File target = new File(System.getProperty("user.dir"), "target");
    File kdcDir = new File(target, TestThriftSpnegoHttpServer.class.getSimpleName());
    if (kdcDir.exists()) {
      FileUtils.deleteDirectory(kdcDir);
    }
    kdcDir.mkdirs();
    kdc.setWorkDir(kdcDir);

    kdc.setKdcHost(HConstants.LOCALHOST);
    int kdcPort = HBaseTestingUtility.randomFreePort();
    kdc.setAllowTcp(true);
    kdc.setAllowUdp(false);
    kdc.setKdcTcpPort(kdcPort);

    LOG.info("Starting KDC server at " + HConstants.LOCALHOST + ":" + kdcPort);

    kdc.init();

    return kdc;
  }

};