//,temp,ApplicationMaster.java,1776,1812,temp,ApplicationMaster.java,1654,1687
//,3
public class xxx {
  private void publishContainerStartEventOnTimelineServiceV2(
      Container container, long startTime) {
    final org.apache.hadoop.yarn.api.records.timelineservice.TimelineEntity
        entity =
            new org.apache.hadoop.yarn.api.records.timelineservice.
            TimelineEntity();
    entity.setId(container.getId().toString());
    entity.setType(DSEntity.DS_CONTAINER.toString());
    entity.setCreatedTime(startTime);
    entity.addInfo("user", appSubmitterUgi.getShortUserName());

    org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent event =
        new org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent();
    event.setTimestamp(startTime);
    event.setId(DSEvent.DS_CONTAINER_START.toString());
    event.addInfo("Node", container.getNodeId().toString());
    event.addInfo("Resources", container.getResource().toString());
    entity.addEvent(event);
    entity.setIdPrefix(TimelineServiceHelper.invertLong(startTime));

    try {
      appSubmitterUgi.doAs(new PrivilegedExceptionAction<Object>() {
        @Override
        public TimelinePutResponse run() throws Exception {
          timelineV2Client.putEntitiesAsync(entity);
          return null;
        }
      });
    } catch (Exception e) {
      LOG.error("Container start event could not be published for "
          + container.getId().toString(),
          e instanceof UndeclaredThrowableException ? e.getCause() : e);
    }
  }

};