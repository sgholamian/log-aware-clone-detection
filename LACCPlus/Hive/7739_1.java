//,temp,QTestMetaStoreHandler.java,62,76,temp,ITestDbTxnManager.java,44,66
//,3
public class xxx {
  public QTestMetaStoreHandler setMetaStoreConfiguration(HiveConf conf) {
    conf.setVar(ConfVars.METASTOREDBTYPE, getDbTypeConfString());

    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECT_URL_KEY, rule.getJdbcUrl());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECTION_DRIVER, rule.getJdbcDriver());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECTION_USER_NAME, rule.getHiveUser());
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.PWD, rule.getHivePassword());
    // In this case we can disable auto_create which is enabled by default for every test
    MetastoreConf.setBoolVar(conf, MetastoreConf.ConfVars.AUTO_CREATE_ALL, false);

    LOG.info(String.format("set metastore connection to url: %s",
        MetastoreConf.getVar(conf, MetastoreConf.ConfVars.CONNECT_URL_KEY)));

    return this;
  }

};