//,temp,SharedCache.java,674,688,temp,QueryTracker.java,258,272
//,3
public class xxx {
  List<QueryFragmentInfo> getRegisteredFragments(QueryIdentifier queryIdentifier) {
    ReadWriteLock dagLock = getDagLock(queryIdentifier);
    dagLock.readLock().lock();
    try {
      QueryInfo queryInfo = queryInfoMap.get(queryIdentifier);
      if (queryInfo == null) {
        // Race with queryComplete
        LOG.warn("Unknown query: Returning an empty list of fragments");
        return Collections.emptyList();
      }
      return queryInfo.getRegisteredFragments();
    } finally {
      dagLock.readLock().unlock();
    }
  }

};