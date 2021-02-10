//,temp,ApplicationMaster.java,1776,1812,temp,ApplicationMaster.java,1741,1774
//,3
public class xxx {
  private void publishContainerEndEventOnTimelineServiceV2(
      final ContainerStatus container, long containerStartTime) {
    final org.apache.hadoop.yarn.api.records.timelineservice.TimelineEntity
        entity =
            new org.apache.hadoop.yarn.api.records.timelineservice.
            TimelineEntity();
    entity.setId(container.getContainerId().toString());
    entity.setType(DSEntity.DS_CONTAINER.toString());
    //entity.setDomainId(domainId);
    entity.addInfo("user", appSubmitterUgi.getShortUserName());
    org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent event =
        new  org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent();
    event.setTimestamp(System.currentTimeMillis());
    event.setId(DSEvent.DS_CONTAINER_END.toString());
    event.addInfo("State", container.getState().name());
    event.addInfo("Exit Status", container.getExitStatus());
    event.addInfo(DIAGNOSTICS, container.getDiagnostics());
    entity.addEvent(event);
    entity.setIdPrefix(TimelineServiceHelper.invertLong(containerStartTime));

    try {
      appSubmitterUgi.doAs(new PrivilegedExceptionAction<Object>() {
        @Override
        public TimelinePutResponse run() throws Exception {
          timelineV2Client.putEntitiesAsync(entity);
          return null;
        }
      });
    } catch (Exception e) {
      LOG.error("Container end event could not be published for "
          + container.getContainerId().toString(),
          e instanceof UndeclaredThrowableException ? e.getCause() : e);
    }
  }

};