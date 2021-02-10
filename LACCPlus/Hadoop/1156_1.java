//,temp,EditLogTailer_after_fix.java,104,131,temp,EditLogTailer.java,104,131
//,2
public class xxx {
  public EditLogTailer(FSNamesystem namesystem, Configuration conf) {
    this.tailerThread = new EditLogTailerThread();
    this.conf = conf;
    this.namesystem = namesystem;
    this.editLog = namesystem.getEditLog();
    
    lastLoadTimeMs = monotonicNow();

    logRollPeriodMs = conf.getInt(DFSConfigKeys.DFS_HA_LOGROLL_PERIOD_KEY,
        DFSConfigKeys.DFS_HA_LOGROLL_PERIOD_DEFAULT) * 1000;
    if (logRollPeriodMs >= 0) {
      this.activeAddr = getActiveNodeAddress();
      Preconditions.checkArgument(activeAddr.getPort() > 0,
          "Active NameNode must have an IPC port configured. " +
          "Got address '%s'", activeAddr);
      LOG.info("Will roll logs on active node at " + activeAddr + " every " +
          (logRollPeriodMs / 1000) + " seconds.");
    } else {
      LOG.info("Not going to trigger log rolls on active node because " +
          DFSConfigKeys.DFS_HA_LOGROLL_PERIOD_KEY + " is negative.");
    }
    
    sleepTimeMs = conf.getInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY,
        DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_DEFAULT) * 1000;
    
    LOG.debug("logRollPeriodMs=" + logRollPeriodMs +
        " sleepTime=" + sleepTimeMs);
  }

};