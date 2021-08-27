//,temp,HiveMetaStoreClient.java,597,797,temp,HiveMetaStoreClientPreCatalog.java,435,582
//,3
public class xxx {
  private void open() throws MetaException {
    isConnected = false;
    TTransportException tte = null;
    MetaException recentME = null;
    boolean useSSL = MetastoreConf.getBoolVar(conf, ConfVars.USE_SSL);
    boolean useSasl = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_SASL);
    String clientAuthMode = MetastoreConf.getVar(conf, ConfVars.METASTORE_CLIENT_AUTH_MODE);
    boolean usePasswordAuth = false;
    boolean useFramedTransport = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_FRAMED_TRANSPORT);
    boolean useCompactProtocol = MetastoreConf.getBoolVar(conf, ConfVars.USE_THRIFT_COMPACT_PROTOCOL);
    int clientSocketTimeout = (int) MetastoreConf.getTimeVar(conf,
        ConfVars.CLIENT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);

    if (clientAuthMode != null) {
      usePasswordAuth = "PLAIN".equalsIgnoreCase(clientAuthMode);
    }

    for (int attempt = 0; !isConnected && attempt < retries; ++attempt) {
      for (URI store : metastoreUris) {
        LOG.info("Trying to connect to metastore with URI ({})", store);

        try {
          if (useSSL) {
            try {
              String trustStorePath = MetastoreConf.getVar(conf, ConfVars.SSL_TRUSTSTORE_PATH).trim();
              if (trustStorePath.isEmpty()) {
                throw new IllegalArgumentException(ConfVars.SSL_TRUSTSTORE_PATH
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
                  trustStorePath, trustStorePassword, trustStoreType, trustStoreAlgorithm);
              final int newCount = connCount.incrementAndGet();
              LOG.debug(
                  "Opened an SSL connection to metastore, current connections: {}",
                  newCount);
              if (LOG.isTraceEnabled()) {
                LOG.trace("METASTORE SSL CONNECTION TRACE - open [{}]",
                    System.identityHashCode(this), new Exception());
              }
            } catch (IOException e) {
              throw new IllegalArgumentException(e);
            } catch (TTransportException e) {
              tte = e;
              throw new MetaException(e.toString());
            }
          } else {
            transport = new TSocket(store.getHost(), store.getPort(), clientSocketTimeout);
          }

          if (usePasswordAuth) {
            // we are using PLAIN Sasl connection with user/password
            LOG.debug("HMSC::open(): Creating plain authentication thrift connection.");
            String userName = MetastoreConf.getVar(conf, ConfVars.METASTORE_CLIENT_PLAIN_USERNAME);

            if (null == userName || userName.isEmpty()) {
              throw new MetaException("No user specified for plain transport.");
            }

            // The password is not directly provided. It should be obtained from a keystore pointed
            // by configuration "hadoop.security.credential.provider.path".
            try {
              String passwd = null;
              char[] pwdCharArray = conf.getPassword(userName);
              if (null != pwdCharArray) {
                passwd = new String(pwdCharArray);
              }
              if (null == passwd) {
                throw new MetaException("No password found for user " + userName);
              }
              // Overlay the SASL transport on top of the base socket transport (SSL or non-SSL)
              transport = MetaStorePlainSaslHelper.getPlainTransport(userName, passwd, transport);
            } catch (IOException sasle) {
              // IOException covers SaslException
              LOG.error("Could not create client transport", sasle);
              throw new MetaException(sasle.toString());
            }
          } else if (useSasl) {
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

              if (tokenStrForm != null) {
                LOG.debug("HMSC::open(): Found delegation token. Creating DIGEST-based thrift connection.");
                // authenticate using delegation tokens via the "DIGEST" mechanism
                transport = authBridge.createClientTransport(null, store.getHost(),
                    "DIGEST", tokenStrForm, transport,
                        MetaStoreUtils.getMetaStoreSaslProperties(conf, useSSL));
              } else {
                LOG.debug("HMSC::open(): Could not find delegation token. Creating KERBEROS-based thrift connection.");
                String principalConfig =
                    MetastoreConf.getVar(conf, ConfVars.KERBEROS_PRINCIPAL);
                transport = authBridge.createClientTransport(
                    principalConfig, store.getHost(), "KERBEROS", null,
                    transport, MetaStoreUtils.getMetaStoreSaslProperties(conf, useSSL));
              }
            } catch (IOException ioe) {
              LOG.error("Failed to create client transport", ioe);
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
              final int newCount = connCount.incrementAndGet();
              LOG.info("Opened a connection to metastore, URI ({}) "
                  + "current connections: {}", store, newCount);
              if (LOG.isTraceEnabled()) {
                LOG.trace("METASTORE CONNECTION TRACE - open [{}]",
                    System.identityHashCode(this), new Exception());
              }
            }
            isConnected = true;
          } catch (TTransportException e) {
            tte = e;
            LOG.warn("Failed to connect to the MetaStore Server URI ({})",
                store);
            LOG.debug("Failed to connect to the MetaStore Server URI ({})",
                store, e);
          }

          if (isConnected && !useSasl && !usePasswordAuth &&
                  MetastoreConf.getBoolVar(conf, ConfVars.EXECUTE_SET_UGI)) {
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
          recentME = e;
          LOG.error("Failed to connect to metastore with URI (" + store
              + ") in attempt " + attempt, e);
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
      // Either tte or recentME should be set but protect from a bug which causes both of them to
      // be null. When MetaException wraps TTransportException, tte will be set so stringify that
      // directly.
      String exceptionString = "Unknown exception";
      if (tte != null) {
        exceptionString = StringUtils.stringifyException(tte);
      } else if (recentME != null) {
        exceptionString = StringUtils.stringifyException(recentME);
      }
      throw new MetaException("Could not connect to meta store using any of the URIs provided." +
          " Most recent failure: " + exceptionString);
    }

    snapshotActiveConf();
  }

};