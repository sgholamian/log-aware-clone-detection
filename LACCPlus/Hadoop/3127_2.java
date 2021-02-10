//,temp,EntityGroupFSTimelineStore.java,1076,1101,temp,EntityGroupFSTimelineStore.java,1056,1074
//,3
public class xxx {
  @Override
  public TimelineEntity getEntity(String entityId, String entityType,
      EnumSet<Field> fieldsToRetrieve) throws IOException {
    LOG.debug("getEntity type={} id={}", entityType, entityId);
    List<EntityCacheItem> relatedCacheItems = new ArrayList<>();
    List<TimelineStore> stores = getTimelineStoresForRead(entityId, entityType,
        relatedCacheItems);
    for (TimelineStore store : stores) {
      LOG.debug("Try timeline store {}:{} for the request", store.getName(),
          store.toString());
      TimelineEntity e =
          store.getEntity(entityId, entityType, fieldsToRetrieve);
      if (e != null) {
        return e;
      }
    }
    LOG.debug("getEntity: Found nothing");
    return null;
  }

};