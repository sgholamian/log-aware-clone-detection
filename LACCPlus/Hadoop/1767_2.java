//,temp,ApplicationMaster.java,1163,1183,temp,ApplicationMaster.java,1141,1161
//,3
public class xxx {
  private static void publishContainerEndEvent(
      final TimelineClient timelineClient, ContainerStatus container,
      String domainId, UserGroupInformation ugi) {
    final TimelineEntity entity = new TimelineEntity();
    entity.setEntityId(container.getContainerId().toString());
    entity.setEntityType(DSEntity.DS_CONTAINER.toString());
    entity.setDomainId(domainId);
    entity.addPrimaryFilter("user", ugi.getShortUserName());
    TimelineEvent event = new TimelineEvent();
    event.setTimestamp(System.currentTimeMillis());
    event.setEventType(DSEvent.DS_CONTAINER_END.toString());
    event.addEventInfo("State", container.getState().name());
    event.addEventInfo("Exit Status", container.getExitStatus());
    entity.addEvent(event);
    try {
      timelineClient.putEntities(entity);
    } catch (YarnException | IOException e) {
      LOG.error("Container end event could not be published for "
          + container.getContainerId().toString(), e);
    }
  }

};