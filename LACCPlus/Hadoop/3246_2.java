//,temp,FairScheduler.java,1202,1308,temp,CapacityScheduler.java,1699,1863
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

      updateNodeLabelsAndQueueResource(labelUpdateEvent);
    }
    break;
    case NODE_UPDATE:
    {
      NodeUpdateSchedulerEvent nodeUpdatedEvent = (NodeUpdateSchedulerEvent)event;
      nodeUpdate(nodeUpdatedEvent.getRMNode());
    }
    break;
    case APP_ADDED:
    {
      AppAddedSchedulerEvent appAddedEvent = (AppAddedSchedulerEvent) event;
      String queueName = resolveReservationQueueName(appAddedEvent.getQueue(),
          appAddedEvent.getApplicationId(), appAddedEvent.getReservationID(),
          appAddedEvent.getIsAppRecovering());
      if (queueName != null) {
        if (!appAddedEvent.getIsAppRecovering()) {
          addApplication(appAddedEvent.getApplicationId(), queueName,
              appAddedEvent.getUser(), appAddedEvent.getApplicatonPriority(),
              appAddedEvent.getPlacementContext());
        } else {
          addApplicationOnRecovery(appAddedEvent.getApplicationId(), queueName,
              appAddedEvent.getUser(), appAddedEvent.getApplicatonPriority(),
              appAddedEvent.getPlacementContext());
        }
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
      if (containerExpiredEvent.isIncrease()) {
        rollbackContainerUpdate(containerId);
      } else {
        completedContainer(getRMContainer(containerId),
            SchedulerUtils.createAbnormalContainerStatus(
                containerId,
                SchedulerUtils.EXPIRED_CONTAINER),
            RMContainerEventType.EXPIRE);
      }
    }
    break;
    case RELEASE_CONTAINER:
    {
      RMContainer container = ((ReleaseContainerEvent) event).getContainer();
      completedContainer(container,
          SchedulerUtils.createAbnormalContainerStatus(
            container.getContainerId(),
            SchedulerUtils.RELEASED_CONTAINER),
          RMContainerEventType.RELEASED);
    }
    break;
    case KILL_RESERVED_CONTAINER:
    {
      ContainerPreemptEvent killReservedContainerEvent =
          (ContainerPreemptEvent) event;
      RMContainer container = killReservedContainerEvent.getContainer();
      killReservedContainer(container);
    }
    break;
    case MARK_CONTAINER_FOR_PREEMPTION:
    {
      ContainerPreemptEvent preemptContainerEvent =
          (ContainerPreemptEvent)event;
      ApplicationAttemptId aid = preemptContainerEvent.getAppId();
      RMContainer containerToBePreempted = preemptContainerEvent.getContainer();
      markContainerForPreemption(aid, containerToBePreempted);
    }
    break;
    case MARK_CONTAINER_FOR_KILLABLE:
    {
      ContainerPreemptEvent containerKillableEvent = (ContainerPreemptEvent)event;
      RMContainer killableContainer = containerKillableEvent.getContainer();
      markContainerForKillable(killableContainer);
    }
    break;
    case MARK_CONTAINER_FOR_NONKILLABLE:
    {
      if (isLazyPreemptionEnabled) {
        ContainerPreemptEvent cancelKillContainerEvent =
            (ContainerPreemptEvent) event;
        markContainerForNonKillable(cancelKillContainerEvent.getContainer());
      }
    }
    break;
    case MANAGE_QUEUE:
    {
      QueueManagementChangeEvent queueManagementChangeEvent =
          (QueueManagementChangeEvent) event;
      ParentQueue parentQueue = queueManagementChangeEvent.getParentQueue();
      try {
        final List<QueueManagementChange> queueManagementChanges =
            queueManagementChangeEvent.getQueueManagementChanges();
        ((ManagedParentQueue) parentQueue)
            .validateAndApplyQueueManagementChanges(queueManagementChanges);
      } catch (SchedulerDynamicEditException sde) {
        LOG.error("Queue Management Change event cannot be applied for "
            + "parent queue : " + parentQueue.getQueueName(), sde);
      } catch (IOException ioe) {
        LOG.error("Queue Management Change event cannot be applied for "
            + "parent queue : " + parentQueue.getQueueName(), ioe);
      }
    }
    break;
    default:
      LOG.error("Invalid eventtype " + event.getType() + ". Ignoring!");
    }
  }

};