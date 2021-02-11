//,temp,Job.java,1710,1721,temp,CapacitySchedulerConfiguration.java,919,927
//,3
public class xxx {
  public int getOffSwitchPerHeartbeatLimit() {
    int limit = getInt(OFFSWITCH_PER_HEARTBEAT_LIMIT,
        DEFAULT_OFFSWITCH_PER_HEARTBEAT_LIMIT);
    if (limit < 1) {
      LOG.warn(OFFSWITCH_PER_HEARTBEAT_LIMIT + "(" + limit + ") < 1. Using 1.");
      limit = 1;
    }
    return limit;
  }

};