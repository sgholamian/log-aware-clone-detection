//,temp,FifoScheduler.java,985,993,temp,FairScheduler.java,844,852
//,2
public class xxx {
  @VisibleForTesting
  @Override
  public void killContainer(RMContainer container) {
    ContainerStatus status = SchedulerUtils.createKilledContainerStatus(
        container.getContainerId(),
        "Killed by RM to simulate an AM container failure");
    LOG.info("Killing container " + container);
    completedContainer(container, status, RMContainerEventType.KILL);
  }

};