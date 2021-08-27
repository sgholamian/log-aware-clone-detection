//,temp,LlapTaskUmbilicalServer.java,58,104,temp,LlapTaskCommunicator.java,253,307
//,3
public class xxx {
  @Override
  protected void startRpcServer() {
    Configuration conf = getConf();
    try {
      JobTokenSecretManager jobTokenSecretManager =
          new JobTokenSecretManager();
      jobTokenSecretManager.addTokenForJob(tokenIdentifier, sessionToken);

      int numHandlers =
          HiveConf.getIntVar(conf, ConfVars.LLAP_TASK_COMMUNICATOR_LISTENER_THREAD_COUNT);
      String[] portRange =
          conf.get(HiveConf.ConfVars.LLAP_TASK_UMBILICAL_SERVER_PORT.varname)
              .split("-");
      boolean isHadoopSecurityAuthorizationEnabled = conf.getBoolean(
          CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHORIZATION, false);
      boolean portFound = false;
      IOException ioe = null;
      int minPort = Integer.parseInt(portRange[0]);
      if (portRange.length == 1) {
        // Single port specified, not range.
        startServerInternal(conf, minPort, numHandlers, jobTokenSecretManager,
            isHadoopSecurityAuthorizationEnabled);
        portFound = true;
        LOG.info("Successfully bound to port {}", minPort);
      } else {
        int maxPort = Integer.parseInt(portRange[1]);
        // Validate the range specified is valid. i.e the ports lie between
        // 1024 and 65535.
        validatePortRange(portRange[0], portRange[1]);

        for (int i = minPort; i < maxPort; i++) {
          try {
            startServerInternal(conf, i, numHandlers, jobTokenSecretManager,
                isHadoopSecurityAuthorizationEnabled);
            portFound = true;
            LOG.info("Successfully bound to port {}", i);
            break;
          } catch (BindException be) {
            // Ignore and move ahead, in search of a free port.
            LOG.warn("Unable to bind to port {}", i, be);
            ioe = be;
          }
        }
      }
      if(!portFound) {
        throw ioe;
      }
      this.address = NetUtils.getConnectAddress(server);
      this.amHost = LlapUtil.getAmHostNameFromAddress(address, conf);
      LOG.info("Started LlapUmbilical: " + umbilical.getClass().getName() + " at address: "
          + address + " with numHandlers=" + numHandlers + " using the host name " + amHost);
    } catch (IOException e) {
      throw new TezUncheckedException(e);
    }
  }

};