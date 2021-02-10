//,temp,CapacityScheduler.java,1545,1553,temp,CapacityScheduler.java,1521,1531
//,2
public class xxx {
  @Override
  public void killContainer(RMContainer cont) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("KILL_CONTAINER: container" + cont.toString());
    }
    completedContainer(cont, SchedulerUtils.createPreemptedContainerStatus(
      cont.getContainerId(), SchedulerUtils.PREEMPTED_CONTAINER),
      RMContainerEventType.KILL);
  }

};