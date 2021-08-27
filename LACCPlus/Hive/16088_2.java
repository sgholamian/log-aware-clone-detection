//,temp,HiveMetaStoreClient.java,597,797,temp,HiveMetaStoreClientPreCatalog.java,435,582
//,3
public class xxx {
  private void open() throws MetaException {
    isConnected = false;
    TTransportException tte = null;
    boolean useSSL = MetastoreConf.getBoolVar(conf, ConfVars.USE_SSL);
    boolean useSasl = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_SASL);
    boolean useFramedTransport = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_FRAMED_TRANSPORT);
    boolean useCompactProtocol = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_COMPACT_PROTOCOL);
    int clientSocketTimeout = (int) MetastoreConf.getTimeVar(conf,
        ConfVars.CLIENT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);

    for (int attempt = 0; !isConnected && attempt < retries; ++attempt) {
      for (URI store : metastoreUris) {
        LOG.info("Trying to connect to metastore with URI " + store);

        try {
          if (useSSL) {
            try {
              String trustStorePath = MetastoreConf.getVar(conf, ConfVars.SSL_TRUSTSTORE_PATH).trim();
              if (trustStorePath.isEmpty()) {
                throw new IllegalArgumentException(ConfVars.SSL_TRUSTSTORE_PATH.toString()
                    + " Not configured for SSL connection");
              }
              String trustStorePassword =
                  MetastoreConf.getPassword(conf, MetastoreConf.ConfVars.SSL_TRUSTSTORE_PASSWORD);
              String trustStoreType =
                      MetastoreConf.getVar(conf, ConfVars.SSL_TRUSTSTORE_TYPE).trim();
              String trustStoreAlgorithm =
                      MetastoreConf.getVar(conf, ConfVars.SSL_TRUSTMANAGERFACTORY_ALGORITHM).trim();


              // Create an SSL socket and connect
              transport = SecurityUtils.getSSLSocket(store.getHost(), store.getPort(), clientSocketTimeout,
                  trustStorePath, trustStorePassword, trustStoreType, trustStoreAlgorithm );
              LOG.info("Opened an SSL connection to metastore, current connections: " + connCount.incrementAndGet());
            } catch(IOException e) {
              throw new IllegalArgumentException(e);
            } catch(TTransportException e) {
              tte = e;
              throw new MetaException(e.toString());
            }
          } else {
            transport = new TSocket(store.getHost(), store.getPort(), clientSocketTimeout);
          }

          if (useSasl) {
            // Wrap thrift connection with SASL for secure connection.
            try {
              HadoopThriftAuthBridge.Client authBridge =
                HadoopThriftAuthBridge.getBridge().createClient();

              // check if we should use delegation tokens to authenticate
              // the call below gets hold of the tokens if they are set up by hadoop
              // this should happen on the map/reduce tasks if the client added the
              // tokens into hadoop's credential store in the front end during job
              // submission.
              String tokenSig = MetastoreConf.getVar(conf, ConfVars.TOKEN_SIGNATURE);
              // tokenSig could be null
              tokenStrForm = SecurityUtils.getTokenStrForm(tokenSig);

              if(tokenStrForm != null) {
                LOG.info("HMSC::open(): Found delegation token. Creating DIGEST-based thrift connection.");
                // authenticate using delegation tokens via the "DIGEST" mechanism
                transport = authBridge.createClientTransport(null, store.getHost(),
                    "DIGEST", tokenStrForm, transport,
                        MetaStoreUtils.getMetaStoreSaslProperties(conf, useSSL));
              } else {
                LOG.info("HMSC::open(): Could not find delegation token. Creating KERBEROS-based thrift connection.");
                String principalConfig =
                    MetastoreConf.getVar(conf, ConfVars.KERBEROS_PRINCIPAL);
                transport = authBridge.createClientTransport(
                    principalConfig, store.getHost(), "KERBEROS", null,
                    transport, MetaStoreUtils.getMetaStoreSaslProperties(conf, useSSL));
              }
            } catch (IOException ioe) {
              LOG.error("Couldn't create client transport", ioe);
              throw new MetaException(ioe.toString());
            }
          } else {
            if (useFramedTransport) {
              transport = new TFramedTransport(transport);
            }
          }

          final TProtocol protocol;
          if (useCompactProtocol) {
            protocol = new TCompactProtocol(transport);
          } else {
            protocol = new TBinaryProtocol(transport);
          }
          client = new ThriftHiveMetastore.Client(protocol);
          try {
            if (!transport.isOpen()) {
              transport.open();
              LOG.info("Opened a connection to metastore, current connections: " + connCount.incrementAndGet());
            }
            isConnected = true;
          } catch (TTransportException e) {
            tte = e;
            if (LOG.isDebugEnabled()) {
              LOG.warn("Failed to connect to the MetaStore Server...", e);
            } else {
              // Don't print full exception trace if DEBUG is not on.
              LOG.warn("Failed to connect to the MetaStore Server...");
            }
          }

          if (isConnected && !useSasl && MetastoreConf.getBoolVar(conf, ConfVars.EXECUTE_SET_UGI)){
            // Call set_ugi, only in unsecure mode.
            try {
              UserGroupInformation ugi = SecurityUtils.getUGI();
              client.set_ugi(ugi.getUserName(), Arrays.asList(ugi.getGroupNames()));
            } catch (LoginException e) {
              LOG.warn("Failed to do login. set_ugi() is not successful, " +
                       "Continuing without it.", e);
            } catch (IOException e) {
              LOG.warn("Failed to find ugi of client set_ugi() is not successful, " +
                  "Continuing without it.", e);
            } catch (TException e) {
              LOG.warn("set_ugi() not successful, Likely cause: new client talking to old server. "
                  + "Continuing without it.", e);
            }
          }
        } catch (MetaException e) {
          LOG.error("Unable to connect to metastore with URI " + store
                    + " in attempt " + attempt, e);
        }
        if (isConnected) {
          break;
        }
      }
      // Wait before launching the next round of connection retries.
      if (!isConnected && retryDelaySeconds > 0) {
        try {
          LOG.info("Waiting " + retryDelaySeconds + " seconds before next connection attempt.");
          Thread.sleep(retryDelaySeconds * 1000);
        } catch (InterruptedException ignore) {}
      }
    }

    if (!isConnected) {
      throw new MetaException("Could not connect to meta store using any of the URIs provided." +
        " Most recent failure: " + StringUtils.stringifyException(tte));
    }

    snapshotActiveConf();

    LOG.info("Connected to metastore.");
  }

};