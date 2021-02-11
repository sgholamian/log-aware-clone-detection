//,temp,Job.java,1724,1734,temp,Job.java,1710,1721
//,2
public class xxx {
  public static int getCompletionPollInterval(Configuration conf) {
    int completionPollIntervalMillis = conf.getInt(
      COMPLETION_POLL_INTERVAL_KEY, DEFAULT_COMPLETION_POLL_INTERVAL);
    if (completionPollIntervalMillis < 1) { 
      LOG.warn(COMPLETION_POLL_INTERVAL_KEY + 
       " has been set to an invalid value; "
       + "replacing with " + DEFAULT_COMPLETION_POLL_INTERVAL);
      completionPollIntervalMillis = DEFAULT_COMPLETION_POLL_INTERVAL;
    }
    return completionPollIntervalMillis;
  }

};