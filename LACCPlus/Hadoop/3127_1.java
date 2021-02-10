//,temp,EntityGroupFSTimelineStore.java,1076,1101,temp,EntityGroupFSTimelineStore.java,1056,1074
//,3
public class xxx {
  @Override
  public TimelineEvents getEntityTimelines(String entityType,
      SortedSet<String> entityIds, Long limit, Long windowStart,
      Long windowEnd, Set<String> eventTypes) throws IOException {
    LOG.debug("getEntityTimelines type={} ids={}", entityType, entityIds);
    TimelineEvents returnEvents = new TimelineEvents();
    List<EntityCacheItem> relatedCacheItems = new ArrayList<>();
    for (String entityId : entityIds) {
      LOG.debug("getEntityTimeline type={} id={}", entityType, entityId);
      List<TimelineStore> stores
          = getTimelineStoresForRead(entityId, entityType, relatedCacheItems);
      for (TimelineStore store : stores) {
        LOG.debug("Try timeline store {}:{} for the request", store.getName(),
            store.toString());
        SortedSet<String> entityIdSet = new TreeSet<>();
        entityIdSet.add(entityId);
        TimelineEvents events =
            store.getEntityTimelines(entityType, entityIdSet, limit,
                windowStart, windowEnd, eventTypes);
        if (events != null) {
          returnEvents.addEvents(events.getAllEvents());
        }
      }
    }
    return returnEvents;
  }

};