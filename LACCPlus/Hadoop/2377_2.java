//,temp,ApplicationMaster.java,1716,1739,temp,ApplicationMaster.java,1515,1541
//,3
public class xxx {
  private void publishContainerStartEvent(
      final TimelineClient timelineClient, final Container container,
      String domainId, UserGroupInformation ugi) {
    final TimelineEntity entity = new TimelineEntity();
    entity.setEntityId(container.getId().toString());
    entity.setEntityType(DSEntity.DS_CONTAINER.toString());
    entity.setDomainId(domainId);
    entity.addPrimaryFilter(USER_TIMELINE_FILTER_NAME, ugi.getShortUserName());
    entity.addPrimaryFilter(APPID_TIMELINE_FILTER_NAME, container.getId()
        .getApplicationAttemptId().getApplicationId().toString());
    TimelineEvent event = new TimelineEvent();
    event.setTimestamp(System.currentTimeMillis());
    event.setEventType(DSEvent.DS_CONTAINER_START.toString());
    event.addEventInfo("Node", container.getNodeId().toString());
    event.addEventInfo("Resources", container.getResource().toString());
    entity.addEvent(event);

    try {
      processTimelineResponseErrors(
          putContainerEntity(timelineClient,
              container.getId().getApplicationAttemptId(),
              entity));
    } catch (YarnException | IOException | ClientHandlerException e) {
      LOG.error("Container start event could not be published for "
          + container.getId().toString(), e);
    }
  }

};