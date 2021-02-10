//,temp,NMTimelinePublisher.java,273,313,temp,NMTimelinePublisher.java,156,209
//,3
public class xxx {
  private void publishContainerLocalizationEvent(
      ContainerLocalizationEvent event, String eventType) {
    Container container = event.getContainer();
    ContainerId containerId = container.getContainerId();
    TimelineEntity entity = createContainerEntity(containerId);

    TimelineEvent tEvent = new TimelineEvent();
    tEvent.setId(eventType);
    tEvent.setTimestamp(event.getTimestamp());
    entity.addEvent(tEvent);
    entity.setIdPrefix(TimelineServiceHelper.
        invertLong(container.getContainerStartTime()));

    ApplicationId appId =
        container.getContainerId().getApplicationAttemptId().getApplicationId();
    try {
      // no need to put it as part of publisher as timeline client already has
      // Queuing concept
      TimelineV2Client timelineClient = getTimelineClient(appId);
      if (timelineClient != null) {
        timelineClient.putEntitiesAsync(entity);
      } else {
        LOG.error("Seems like client has been removed before the event could be"
            + " published for " + container.getContainerId());
      }
    } catch (IOException e) {
      LOG.error("Failed to publish Container metrics for container "
          + container.getContainerId());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Failed to publish Container metrics for container "
            + container.getContainerId(), e);
      }
    } catch (YarnException e) {
      LOG.error("Failed to publish Container metrics for container "
          + container.getContainerId(), e.getMessage());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Failed to publish Container metrics for container "
            + container.getContainerId(), e);
      }
    }
  }

};