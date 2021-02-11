//,temp,FairScheduler.java,1148,1251,temp,CapacityScheduler.java,1244,1380
//,3
public class xxx {
  @Override
  public void handle(SchedulerEvent event) {
    switch (event.getType()) {
    case NODE_ADDED:
      if (!(event instanceof NodeAddedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      NodeAddedSchedulerEvent nodeAddedEvent = (NodeAddedSchedulerEvent)event;
      addNode(nodeAddedEvent.getContainerReports(),
          nodeAddedEvent.getAddedRMNode());
      break;
    case NODE_REMOVED:
      if (!(event instanceof NodeRemovedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      NodeRemovedSchedulerEvent nodeRemovedEvent = (NodeRemovedSchedulerEvent)event;
      removeNode(nodeRemovedEvent.getRemovedRMNode());
      break;
    case NODE_UPDATE:
      if (!(event instanceof NodeUpdateSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      NodeUpdateSchedulerEvent nodeUpdatedEvent = (NodeUpdateSchedulerEvent)event;
      nodeUpdate(nodeUpdatedEvent.getRMNode());
      break;
    case APP_ADDED:
      if (!(event instanceof AppAddedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      AppAddedSchedulerEvent appAddedEvent = (AppAddedSchedulerEvent) event;
      String queueName =
          resolveReservationQueueName(appAddedEvent.getQueue(),
              appAddedEvent.getApplicationId(),
              appAddedEvent.getReservationID());
      if (queueName != null) {
        addApplication(appAddedEvent.getApplicationId(),
            queueName, appAddedEvent.getUser(),
            appAddedEvent.getIsAppRecovering());
      }
      break;
    case APP_REMOVED:
      if (!(event instanceof AppRemovedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      AppRemovedSchedulerEvent appRemovedEvent = (AppRemovedSchedulerEvent)event;
      removeApplication(appRemovedEvent.getApplicationID(),
        appRemovedEvent.getFinalState());
      break;
    case NODE_RESOURCE_UPDATE:
      if (!(event instanceof NodeResourceUpdateSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      NodeResourceUpdateSchedulerEvent nodeResourceUpdatedEvent = 
          (NodeResourceUpdateSchedulerEvent)event;
      updateNodeResource(nodeResourceUpdatedEvent.getRMNode(),
            nodeResourceUpdatedEvent.getResourceOption());
      break;
    case APP_ATTEMPT_ADDED:
      if (!(event instanceof AppAttemptAddedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      AppAttemptAddedSchedulerEvent appAttemptAddedEvent =
          (AppAttemptAddedSchedulerEvent) event;
      addApplicationAttempt(appAttemptAddedEvent.getApplicationAttemptId(),
        appAttemptAddedEvent.getTransferStateFromPreviousAttempt(),
        appAttemptAddedEvent.getIsAttemptRecovering());
      break;
    case APP_ATTEMPT_REMOVED:
      if (!(event instanceof AppAttemptRemovedSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      AppAttemptRemovedSchedulerEvent appAttemptRemovedEvent =
          (AppAttemptRemovedSchedulerEvent) event;
      removeApplicationAttempt(
          appAttemptRemovedEvent.getApplicationAttemptID(),
          appAttemptRemovedEvent.getFinalAttemptState(),
          appAttemptRemovedEvent.getKeepContainersAcrossAppAttempts());
      break;
    case CONTAINER_EXPIRED:
      if (!(event instanceof ContainerExpiredSchedulerEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      ContainerExpiredSchedulerEvent containerExpiredEvent =
          (ContainerExpiredSchedulerEvent)event;
      ContainerId containerId = containerExpiredEvent.getContainerId();
      completedContainer(getRMContainer(containerId),
          SchedulerUtils.createAbnormalContainerStatus(
              containerId,
              SchedulerUtils.EXPIRED_CONTAINER),
          RMContainerEventType.EXPIRE);
      break;
    case CONTAINER_RESCHEDULED:
      if (!(event instanceof ContainerRescheduledEvent)) {
        throw new RuntimeException("Unexpected event type: " + event);
      }
      ContainerRescheduledEvent containerRescheduledEvent =
          (ContainerRescheduledEvent) event;
      RMContainer container = containerRescheduledEvent.getContainer();
      recoverResourceRequestForContainer(container);
      break;
    default:
      LOG.error("Unknown event arrived at FairScheduler: " + event.toString());
    }
  }

};