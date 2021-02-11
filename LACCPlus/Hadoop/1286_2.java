//,temp,TestSecureRegistry.java,167,190,temp,TestSecureRegistry.java,113,139
//,3
public class xxx {
  @Test
  public void testZookeeperCanWrite() throws Throwable {

    System.setProperty("curator-log-events", "true");
    startSecureZK();
    CuratorService curator = null;
    LoginContext login = login(ZOOKEEPER_LOCALHOST,
        ZOOKEEPER_CLIENT_CONTEXT,
        keytab_zk);
    try {
      logLoginDetails(ZOOKEEPER, login);
      RegistrySecurity.setZKSaslClientProperties(ZOOKEEPER,
                                                ZOOKEEPER_CLIENT_CONTEXT);
      curator = startCuratorServiceInstance("ZK", true);
      LOG.info(curator.toString());

      addToTeardown(curator);
      curator.zkMkPath("/", CreateMode.PERSISTENT, false,
          RegistrySecurity.WorldReadWriteACL);
      curator.zkList("/");
      curator.zkMkPath("/zookeeper", CreateMode.PERSISTENT, false,
          RegistrySecurity.WorldReadWriteACL);
    } finally {
      logout(login);
      ServiceOperations.stop(curator);
    }
  }

};