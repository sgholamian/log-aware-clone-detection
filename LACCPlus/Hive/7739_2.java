//,temp,QTestMetaStoreHandler.java,62,76,temp,ITestDbTxnManager.java,44,66
//,3
public class xxx {
  @BeforeClass
  public static void setupDb() throws Exception {
    String metastoreType =
        System.getProperty(SYS_PROP_METASTORE_DB) == null ? "derby" : System.getProperty(SYS_PROP_METASTORE_DB)
            .toLowerCase();
    rule = getDatabaseRule(metastoreType).setVerbose(false);

    conf.setVar(HiveConf.ConfVars.METASTOREDBTYPE, metastoreType.toUpperCase());

    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECT_URL_KEY, rule.getJdbcUrl());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECTION_DRIVER, rule.getJdbcDriver());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECTION_USER_NAME, rule.getHiveUser());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.PWD, rule.getHivePassword());
    // In this case we disable auto_create which is enabled by default for every test
    MetastoreConf.setBoolVar(conf, MetastoreConf.ConfVars.AUTO_CREATE_ALL, false);


    LOG.info("Set metastore connection to url: {}",
        MetastoreConf.getVar(conf, MetastoreConf.ConfVars.CONNECT_URL_KEY));
    // Start the docker container and create the hive user
    rule.before();
    rule.install();
  }

};