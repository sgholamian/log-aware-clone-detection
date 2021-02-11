//,temp,ApplicationMaster.java,1716,1739,temp,ApplicationMaster.java,1587,1608
//,3
public class xxx {
  private void publishApplicationAttemptEvent(
      final TimelineClient timelineClient, String appAttemptId,
      DSEvent appEvent, String domainId, UserGroupInformation ugi) {
    final TimelineEntity entity = new TimelineEntity();
    entity.setEntityId(appAttemptId);
    entity.setEntityType(DSEntity.DS_APP_ATTEMPT.toString());
    entity.setDomainId(domainId);
    entity.addPrimaryFilter(USER_TIMELINE_FILTER_NAME, ugi.getShortUserName());
    TimelineEvent event = new TimelineEvent();
    event.setEventType(appEvent.toString());
    event.setTimestamp(System.currentTimeMillis());
    entity.addEvent(event);
    try {
      TimelinePutResponse response = timelineClient.putEntities(entity);
      processTimelineResponseErrors(response);
    } catch (YarnException | IOException | ClientHandlerException e) {
      LOG.error("App Attempt "
          + (appEvent.equals(DSEvent.DS_APP_ATTEMPT_START) ? "start" : "end")
          + " event could not be published for "
          + appAttemptID, e);
    }
  }

};