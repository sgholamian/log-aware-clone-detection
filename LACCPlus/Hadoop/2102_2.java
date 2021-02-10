//,temp,FairScheduler.java,1148,1251,temp,CapacityScheduler.java,1244,1380
//,3
public class xxx {
  @Override
  public void handle(SchedulerEvent event) {
    switch(event.getType()) {
    case NODE_ADDED:
    {
      NodeAddedSchedulerEvent nodeAddedEvent = (NodeAddedSchedulerEvent)event;
      addNode(nodeAddedEvent.getAddedRMNode());
      recoverContainersOnNode(nodeAddedEvent.getContainerReports(),
        nodeAddedEvent.getAddedRMNode());
    }
    break;
    case NODE_REMOVED:
    {
      NodeRemovedSchedulerEvent nodeRemovedEvent = (NodeRemovedSchedulerEvent)event;
      removeNode(nodeRemovedEvent.getRemovedRMNode());
    }
    break;
    case NODE_RESOURCE_UPDATE:
    {
      NodeResourceUpdateSchedulerEvent nodeResourceUpdatedEvent = 
          (NodeResourceUpdateSchedulerEvent)event;
      updateNodeAndQueueResource(nodeResourceUpdatedEvent.getRMNode(),
        nodeResourceUpdatedEvent.getResourceOption());
    }
    break;
    case NODE_LABELS_UPDATE:
    {
      NodeLabelsUpdateSchedulerEvent labelUpdateEvent =
          (NodeLabelsUpdateSchedulerEvent) event;
      
      for (Entry<NodeId, Set<String>> entry : labelUpdateEvent
          .getUpdatedNodeToLabels().entrySet()) {
        NodeId id = entry.getKey();
        Set<String> labels = entry.getValue();
        updateLabelsOnNode(id, labels);
      }
    }
    break;
    case NODE_UPDATE:
    {
      NodeUpdateSchedulerEvent nodeUpdatedEvent = (NodeUpdateSchedulerEvent)event;
      RMNode node = nodeUpdatedEvent.getRMNode();
      setLastNodeUpdateTime(Time.now());
      nodeUpdate(node);
      if (!scheduleAsynchronously) {
        allocateContainersToNode(getNode(node.getNodeID()));
      }
    }
    break;
    case APP_ADDED:
    {
      AppAddedSchedulerEvent appAddedEvent = (AppAddedSchedulerEvent) event;
      String queueName =
          resolveReservationQueueName(appAddedEvent.getQueue(),
              appAddedEvent.getApplicationId(),
              appAddedEvent.getReservationID());
      if (queueName != null) {
        addApplication(appAddedEvent.getApplicationId(),
            queueName,
            appAddedEvent.getUser(),
            appAddedEvent.getIsAppRecovering(),
            appAddedEvent.getApplicatonPriority());
      }
    }
    break;
    case APP_REMOVED:
    {
      AppRemovedSchedulerEvent appRemovedEvent = (AppRemovedSchedulerEvent)event;
      doneApplication(appRemovedEvent.getApplicationID(),
        appRemovedEvent.getFinalState());
    }
    break;
    case APP_ATTEMPT_ADDED:
    {
      AppAttemptAddedSchedulerEvent appAttemptAddedEvent =
          (AppAttemptAddedSchedulerEvent) event;
      addApplicationAttempt(appAttemptAddedEvent.getApplicationAttemptId(),
        appAttemptAddedEvent.getTransferStateFromPreviousAttempt(),
        appAttemptAddedEvent.getIsAttemptRecovering());
    }
    break;
    case APP_ATTEMPT_REMOVED:
    {
      AppAttemptRemovedSchedulerEvent appAttemptRemovedEvent =
          (AppAttemptRemovedSchedulerEvent) event;
      doneApplicationAttempt(appAttemptRemovedEvent.getApplicationAttemptID(),
        appAttemptRemovedEvent.getFinalAttemptState(),
        appAttemptRemovedEvent.getKeepContainersAcrossAppAttempts());
    }
    break;
    case CONTAINER_EXPIRED:
    {
      ContainerExpiredSchedulerEvent containerExpiredEvent = 
          (ContainerExpiredSchedulerEvent) event;
      ContainerId containerId = containerExpiredEvent.getContainerId();
      completedContainer(getRMContainer(containerId), 
          SchedulerUtils.createAbnormalContainerStatus(
              containerId, 
              SchedulerUtils.EXPIRED_CONTAINER), 
          RMContainerEventType.EXPIRE);
    }
    break;
    case DROP_RESERVATION:
    {
      ContainerPreemptEvent dropReservationEvent = (ContainerPreemptEvent)event;
      RMContainer container = dropReservationEvent.getContainer();
      dropContainerReservation(container);
    }
    break;
    case PREEMPT_CONTAINER:
    {
      ContainerPreemptEvent preemptContainerEvent =
          (ContainerPreemptEvent)event;
      ApplicationAttemptId aid = preemptContainerEvent.getAppId();
      RMContainer containerToBePreempted = preemptContainerEvent.getContainer();
      preemptContainer(aid, containerToBePreempted);
    }
    break;
    case KILL_CONTAINER:
    {
      ContainerPreemptEvent killContainerEvent = (ContainerPreemptEvent)event;
      RMContainer containerToBeKilled = killContainerEvent.getContainer();
      killContainer(containerToBeKilled);
    }
    break;
    case CONTAINER_RESCHEDULED:
    {
      ContainerRescheduledEvent containerRescheduledEvent =
          (ContainerRescheduledEvent) event;
      RMContainer container = containerRescheduledEvent.getContainer();
      recoverResourceRequestForContainer(container);
    }
    break;
    default:
      LOG.error("Invalid eventtype " + event.getType() + ". Ignoring!");
    }
  }

};