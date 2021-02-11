//,temp,sample_7621.java,2,11,temp,sample_7622.java,2,15
//,3
public class xxx {
public TimelineEntity getEntity(String entityId, String entityType, EnumSet<Field> fieldsToRetrieve) throws IOException {
LOG.debug("getEntity type={} id={}", entityType, entityId);
List<EntityCacheItem> relatedCacheItems = new ArrayList<>();
List<TimelineStore> stores = getTimelineStoresForRead(entityId, entityType, relatedCacheItems);
for (TimelineStore store : stores) {


log.info("try timeline store for the request");
}
}

};