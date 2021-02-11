//,temp,ApplicationMaster.java,1716,1739,temp,ApplicationMaster.java,1587,1608
//,3
public class xxx {
  private void publishContainerStartFailedEvent(final ContainerId containerId,
      String diagnostics) {
    final TimelineEntity entityV1 = new TimelineEntity();
    entityV1.setEntityId(containerId.toString());
    entityV1.setEntityType(DSEntity.DS_CONTAINER.toString());
    entityV1.setDomainId(domainId);
    entityV1.addPrimaryFilter(USER_TIMELINE_FILTER_NAME, appSubmitterUgi
        .getShortUserName());
    entityV1.addPrimaryFilter(APPID_TIMELINE_FILTER_NAME,
        containerId.getApplicationAttemptId().getApplicationId().toString());

    TimelineEvent eventV1 = new TimelineEvent();
    eventV1.setTimestamp(System.currentTimeMillis());
    eventV1.setEventType(DSEvent.DS_CONTAINER_END.toString());
    eventV1.addEventInfo(DIAGNOSTICS, diagnostics);
    entityV1.addEvent(eventV1);
    try {
      processTimelineResponseErrors(putContainerEntity(timelineClient,
          containerId.getApplicationAttemptId(), entityV1));
    } catch (YarnException | IOException | ClientHandlerException e) {
      LOG.error("Container end event could not be published for {}",
          containerId, e);
    }
  }

};