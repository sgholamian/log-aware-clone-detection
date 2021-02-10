//,temp,ZKFailoverController.java,189,248,temp,ZKFailoverController_before_log_fix.java,188,247
//,2
public class xxx {
  private int doRun(String[] args)
      throws HadoopIllegalArgumentException, IOException, InterruptedException {
    try {
      initZK();
    } catch (KeeperException ke) {
      LOG.fatal("Unable to start failover controller. Unable to connect "
          + "to ZooKeeper quorum at " + zkQuorum + ". Please check the "
          + "configured value for " + ZK_QUORUM_KEY + " and ensure that "
          + "ZooKeeper is running.");
      return ERR_CODE_NO_ZK;
    }
    if (args.length > 0) {
      if ("-formatZK".equals(args[0])) {
        boolean force = false;
        boolean interactive = true;
        for (int i = 1; i < args.length; i++) {
          if ("-force".equals(args[i])) {
            force = true;
          } else if ("-nonInteractive".equals(args[i])) {
            interactive = false;
          } else {
            badArg(args[i]);
          }
        }
        return formatZK(force, interactive);
      } else {
        badArg(args[0]);
      }
    }

    if (!elector.parentZNodeExists()) {
      LOG.fatal("Unable to start failover controller. "
          + "Parent znode does not exist.\n"
          + "Run with -formatZK flag to initialize ZooKeeper.");
      return ERR_CODE_NO_PARENT_ZNODE;
    }

    try {
      localTarget.checkFencingConfigured();
    } catch (BadFencingConfigurationException e) {
      LOG.fatal("Fencing is not configured for " + localTarget + ".\n" +
          "You must configure a fencing method before using automatic " +
          "failover.", e);
      return ERR_CODE_NO_FENCER;
    }

    initRPC();
    initHM();
    startRPC();
    try {
      mainLoop();
    } finally {
      rpcServer.stopAndJoin();
      
      elector.quitElection(true);
      healthMonitor.shutdown();
      healthMonitor.join();
    }
    return 0;
  }

};