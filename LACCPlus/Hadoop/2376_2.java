//,temp,ApplicationMaster.java,1776,1812,temp,ApplicationMaster.java,1689,1714
//,3
public class xxx {
  private void publishContainerStartFailedEventOnTimelineServiceV2(
      final ContainerId containerId, String diagnostics) {
    final org.apache.hadoop.yarn.api.records.timelineservice.TimelineEntity
        entity = new org.apache.hadoop.yarn.api.records.timelineservice.
        TimelineEntity();
    entity.setId(containerId.toString());
    entity.setType(DSEntity.DS_CONTAINER.toString());
    entity.addInfo("user", appSubmitterUgi.getShortUserName());
    org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent event =
        new org.apache.hadoop.yarn.api.records.timelineservice
            .TimelineEvent();
    event.setTimestamp(System.currentTimeMillis());
    event.setId(DSEvent.DS_CONTAINER_END.toString());
    event.addInfo(DIAGNOSTICS, diagnostics);
    entity.addEvent(event);
    try {
      appSubmitterUgi.doAs((PrivilegedExceptionAction<Object>) () -> {
        timelineV2Client.putEntitiesAsync(entity);
        return null;
      });
    } catch (Exception e) {
      LOG.error("Container start failed event could not be published for {}",
          containerId,
          e instanceof UndeclaredThrowableException ? e.getCause() : e);
    }
  }

};