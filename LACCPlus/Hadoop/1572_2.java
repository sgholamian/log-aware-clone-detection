//,temp,ApplicationMaster.java,1141,1161,temp,ApplicationMaster.java,1112,1139
//,3
public class xxx {
  private static void publishContainerStartEvent(
      final TimelineClient timelineClient, Container container, String domainId,
      UserGroupInformation ugi) {
    final TimelineEntity entity = new TimelineEntity();
    entity.setEntityId(container.getId().toString());
    entity.setEntityType(DSEntity.DS_CONTAINER.toString());
    entity.setDomainId(domainId);
    entity.addPrimaryFilter("user", ugi.getShortUserName());
    TimelineEvent event = new TimelineEvent();
    event.setTimestamp(System.currentTimeMillis());
    event.setEventType(DSEvent.DS_CONTAINER_START.toString());
    event.addEventInfo("Node", container.getNodeId().toString());
    event.addEventInfo("Resources", container.getResource().toString());
    entity.addEvent(event);

    try {
      ugi.doAs(new PrivilegedExceptionAction<TimelinePutResponse>() {
        @Override
        public TimelinePutResponse run() throws Exception {
          return timelineClient.putEntities(entity);
        }
      });
    } catch (Exception e) {
      LOG.error("Container start event could not be published for "
          + container.getId().toString(),
          e instanceof UndeclaredThrowableException ? e.getCause() : e);
    }
  }

};