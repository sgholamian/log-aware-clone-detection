//,temp,ApplicationMaster.java,1163,1183,temp,ApplicationMaster.java,1141,1161
//,3
public class xxx {
  private static void publishApplicationAttemptEvent(
      final TimelineClient timelineClient, String appAttemptId,
      DSEvent appEvent, String domainId, UserGroupInformation ugi) {
    final TimelineEntity entity = new TimelineEntity();
    entity.setEntityId(appAttemptId);
    entity.setEntityType(DSEntity.DS_APP_ATTEMPT.toString());
    entity.setDomainId(domainId);
    entity.addPrimaryFilter("user", ugi.getShortUserName());
    TimelineEvent event = new TimelineEvent();
    event.setEventType(appEvent.toString());
    event.setTimestamp(System.currentTimeMillis());
    entity.addEvent(event);
    try {
      timelineClient.putEntities(entity);
    } catch (YarnException | IOException e) {
      LOG.error("App Attempt "
          + (appEvent.equals(DSEvent.DS_APP_ATTEMPT_START) ? "start" : "end")
          + " event could not be published for "
          + appAttemptId.toString(), e);
    }
  }

};