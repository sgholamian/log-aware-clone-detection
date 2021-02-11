//,temp,AsyncRegionLocator.java,152,161,temp,DeletionListener.java,91,100
//,3
public class xxx {
  void clearCache(TableName tableName) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Clear meta cache for " + tableName);
    }
    if (tableName.equals(META_TABLE_NAME)) {
      metaRegionLocator.clearCache();
    } else {
      nonMetaRegionLocator.clearCache(tableName);
    }
  }

};