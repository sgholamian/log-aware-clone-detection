//,temp,CapacitySchedulerConfiguration.java,377,385,temp,CapacitySchedulerConfiguration.java,359,367
//,3
public class xxx {
  public void setMaximumCapacity(String queue, float maxCapacity) {
    if (maxCapacity > MAXIMUM_CAPACITY_VALUE) {
      throw new IllegalArgumentException("Illegal " +
          "maximum-capacity of " + maxCapacity + " for queue " + queue);
    }
    setFloat(getQueuePrefix(queue) + MAXIMUM_CAPACITY, maxCapacity);
    LOG.debug("CSConf - setMaxCapacity: queuePrefix=" + getQueuePrefix(queue) + 
        ", maxCapacity=" + maxCapacity);
  }

};