//,temp,HiveMetaStoreClient.java,167,262,temp,HiveMetaStoreClientPreCatalog.java,146,227
//,3
public class xxx {
  public HiveMetaStoreClient(Configuration conf, HiveMetaHookLoader hookLoader, Boolean allowEmbedded)
    throws MetaException {

    this.hookLoader = hookLoader;
    if (conf == null) {
      conf = MetastoreConf.newMetastoreConf();
      this.conf = conf;
    } else {
      this.conf = new Configuration(conf);
    }
    version = MetastoreConf.getBoolVar(conf, ConfVars.HIVE_IN_TEST) ? TEST_VERSION : VERSION;
    filterHook = loadFilterHooks();
    isClientFilterEnabled = getIfClientFilterEnabled();
    uriResolverHook = loadUriResolverHook();
    fileMetadataBatchSize = MetastoreConf.getIntVar(
        conf, ConfVars.BATCH_RETRIEVE_OBJECTS_MAX);

    if ((MetastoreConf.get(conf, "hive.metastore.client.capabilities")) != null) {
      String[] capabilities = MetastoreConf.get(conf, "hive.metastore.client.capabilities").split(",");
      setProcessorCapabilities(capabilities);
      String hostName = "unknown";
      try {
        hostName = InetAddress.getLocalHost().getCanonicalHostName();
      } catch (UnknownHostException ue) {
      }
      setProcessorIdentifier("HMSClient-" + "@" + hostName);
    }

    String msUri = MetastoreConf.getVar(conf, ConfVars.THRIFT_URIS);
    localMetaStore = MetastoreConf.isEmbeddedMetaStore(msUri);
    if (localMetaStore) {
      if (!allowEmbedded) {
        throw new MetaException("Embedded metastore is not allowed here. Please configure "
            + ConfVars.THRIFT_URIS.toString() + "; it is currently set to [" + msUri + "]");
      }

      client = callEmbeddedMetastore(this.conf);

      // instantiate the metastore server handler directly instead of connecting
      // through the network
      isConnected = true;
      snapshotActiveConf();
      return;
    }

    // get the number retries
    retries = MetastoreConf.getIntVar(conf, ConfVars.THRIFT_CONNECTION_RETRIES);
    retryDelaySeconds = MetastoreConf.getTimeVar(conf,
        ConfVars.CLIENT_CONNECT_RETRY_DELAY, TimeUnit.SECONDS);

    // user wants file store based configuration
    if (MetastoreConf.getVar(conf, ConfVars.THRIFT_URIS) != null) {
      resolveUris();
    } else {
      LOG.error("NOT getting uris from conf");
      throw new MetaException("MetaStoreURIs not found in conf file");
    }

    //If HADOOP_PROXY_USER is set in env or property,
    //then need to create metastore client that proxies as that user.
    String HADOOP_PROXY_USER = "HADOOP_PROXY_USER";
    String proxyUser = System.getenv(HADOOP_PROXY_USER);
    if (proxyUser == null) {
      proxyUser = System.getProperty(HADOOP_PROXY_USER);
    }
    //if HADOOP_PROXY_USER is set, create DelegationToken using real user
    if (proxyUser != null) {
      LOG.info(HADOOP_PROXY_USER + " is set. Using delegation "
          + "token for HiveMetaStore connection.");
      try {
        UserGroupInformation.getLoginUser().getRealUser().doAs(
            new PrivilegedExceptionAction<Void>() {
              @Override
              public Void run() throws Exception {
                open();
                return null;
              }
            });
        String delegationTokenPropString = "DelegationTokenForHiveMetaStoreServer";
        String delegationTokenStr = getDelegationToken(proxyUser, proxyUser);
        SecurityUtils.setTokenStr(UserGroupInformation.getCurrentUser(), delegationTokenStr,
            delegationTokenPropString);
        MetastoreConf.setVar(this.conf, ConfVars.TOKEN_SIGNATURE, delegationTokenPropString);
        close();
      } catch (Exception e) {
        LOG.error("Error while setting delegation token for " + proxyUser, e);
        if (e instanceof MetaException) {
          throw (MetaException) e;
        } else {
          throw new MetaException(e.getMessage());
        }
      }
    }
    // finally open the store
    open();
  }

};