//,temp,ApplicationMaster.java,1776,1812,temp,ApplicationMaster.java,1689,1714
//,3
public class xxx {
  private void publishApplicationAttemptEventOnTimelineServiceV2(
      DSEvent appEvent) {
    final org.apache.hadoop.yarn.api.records.timelineservice.TimelineEntity
        entity =
            new org.apache.hadoop.yarn.api.records.timelineservice.
            TimelineEntity();
    entity.setId(appAttemptID.toString());
    entity.setType(DSEntity.DS_APP_ATTEMPT.toString());
    long ts = System.currentTimeMillis();
    if (appEvent == DSEvent.DS_APP_ATTEMPT_START) {
      entity.setCreatedTime(ts);
    }
    entity.addInfo("user", appSubmitterUgi.getShortUserName());
    org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent event =
        new org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent();
    event.setId(appEvent.toString());
    event.setTimestamp(ts);
    entity.addEvent(event);
    entity.setIdPrefix(
        TimelineServiceHelper.invertLong(appAttemptID.getAttemptId()));

    try {
      appSubmitterUgi.doAs(new PrivilegedExceptionAction<Object>() {
        @Override
        public TimelinePutResponse run() throws Exception {
          timelineV2Client.putEntitiesAsync(entity);
          return null;
        }
      });
    } catch (Exception e) {
      LOG.error("App Attempt "
          + (appEvent.equals(DSEvent.DS_APP_ATTEMPT_START) ? "start" : "end")
          + " event could not be published for "
          + appAttemptID,
          e instanceof UndeclaredThrowableException ? e.getCause() : e);
    }
  }

};