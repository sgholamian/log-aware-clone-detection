//,temp,LlapTaskUmbilicalServer.java,58,104,temp,LlapTaskCommunicator.java,253,307
//,3
public class xxx {
  public LlapTaskUmbilicalServer(Configuration conf, LlapTaskUmbilicalProtocol umbilical, int numHandlers) throws IOException {
    jobTokenSecretManager = new JobTokenSecretManager();

    String[] portRange =
        conf.get(HiveConf.ConfVars.LLAP_TASK_UMBILICAL_SERVER_PORT.varname)
            .split("-");
    boolean isHadoopSecurityAuthorizationEnabled = conf.getBoolean(
        CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHORIZATION, false);

    int minPort = Integer.parseInt(portRange[0]);
    boolean portFound = false;
    IOException e = null;
    if (portRange.length == 1) {
      // Single port specified, not Range.
      startServer(conf, umbilical, numHandlers, minPort,
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
          startServer(conf, umbilical, numHandlers, i,
              isHadoopSecurityAuthorizationEnabled);
          portFound = true;
          LOG.info("Successfully bound to port {}", i);
          break;
        } catch (BindException be) {
          // Ignore and move ahead, in search of a free port.
          LOG.warn("Unable to bind to port {}", i, be);
          e = be;
        }
      }
    }
    if (!portFound) {
      throw e;
    }

    this.address = NetUtils.getConnectAddress(server);
    LOG.info(
        "Started TaskUmbilicalServer: " + umbilical.getClass().getName() + " at address: " + address +
            " with numHandlers=" + numHandlers);
  }

};