//,temp,CapacitySchedulerConfiguration.java,1147,1151,temp,CapacitySchedulerConfiguration.java,555,559
//,2
public class xxx {
  public void setReservable(String queue, boolean isReservable) {
    setBoolean(getQueuePrefix(queue) + IS_RESERVABLE, isReservable);
    LOG.debug("here setReservableQueue: queuePrefix=" + getQueuePrefix(queue)
        + ", isReservableQueue=" + isReservable(queue));
  }

};