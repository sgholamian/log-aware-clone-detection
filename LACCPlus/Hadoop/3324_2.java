//,temp,Job.java,1724,1734,temp,Job.java,1710,1721
//,2
public class xxx {
  public static int getProgressPollInterval(Configuration conf) {
    // Read progress monitor poll interval from config. Default is 1 second.
    int progMonitorPollIntervalMillis = conf.getInt(
      PROGRESS_MONITOR_POLL_INTERVAL_KEY, DEFAULT_MONITOR_POLL_INTERVAL);
    if (progMonitorPollIntervalMillis < 1) {
      LOG.warn(PROGRESS_MONITOR_POLL_INTERVAL_KEY + 
        " has been set to an invalid value; "
        + " replacing with " + DEFAULT_MONITOR_POLL_INTERVAL);
      progMonitorPollIntervalMillis = DEFAULT_MONITOR_POLL_INTERVAL;
    }
    return progMonitorPollIntervalMillis;
  }

};