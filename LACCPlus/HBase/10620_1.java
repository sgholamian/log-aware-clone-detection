//,temp,TestSpnegoHttpServer.java,144,166,temp,TestThriftSpnegoHttpServer.java,100,122
//,3
public class xxx {
  private static SimpleKdcServer buildMiniKdc() throws Exception {
    SimpleKdcServer kdc = new SimpleKdcServer();

    final File target = new File(System.getProperty("user.dir"), "target");
    File kdcDir = new File(target, TestSpnegoHttpServer.class.getSimpleName());
    if (kdcDir.exists()) {
      deleteRecursively(kdcDir);
    }
    kdcDir.mkdirs();
    kdc.setWorkDir(kdcDir);

    kdc.setKdcHost(KDC_SERVER_HOST);
    int kdcPort = getFreePort();
    kdc.setAllowTcp(true);
    kdc.setAllowUdp(false);
    kdc.setKdcTcpPort(kdcPort);

    LOG.info("Starting KDC server at " + KDC_SERVER_HOST + ":" + kdcPort);

    kdc.init();

    return kdc;
  }

};