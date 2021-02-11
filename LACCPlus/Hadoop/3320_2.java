//,temp,CapacitySchedulerConfiguration.java,1147,1151,temp,CapacitySchedulerConfiguration.java,555,559
//,2
public class xxx {
  public void setUserLimit(String queue, int userLimit) {
    setInt(getQueuePrefix(queue) + USER_LIMIT, userLimit);
    LOG.debug("here setUserLimit: queuePrefix=" + getQueuePrefix(queue) + 
        ", userLimit=" + getUserLimit(queue));
  }

};