//,temp,CapacityScheduler.java,1545,1553,temp,CapacityScheduler.java,1521,1531
//,2
public class xxx {
  @Override
  public void dropContainerReservation(RMContainer container) {
    if(LOG.isDebugEnabled()){
      LOG.debug("DROP_RESERVATION:" + container.toString());
    }
    completedContainer(container,
        SchedulerUtils.createAbnormalContainerStatus(
            container.getContainerId(),
            SchedulerUtils.UNRESERVED_CONTAINER),
        RMContainerEventType.KILL);
  }

};