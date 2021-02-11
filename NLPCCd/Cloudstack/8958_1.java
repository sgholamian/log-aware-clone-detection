//,temp,sample_6974.java,2,12,temp,sample_6976.java,2,12
//,3
public class xxx {
public DataStore getCacheStore(Scope scope) {
if (scope.getScopeType() != ScopeType.ZONE) {
return null;
}
List<DataStore> cacheStores = dataStoreMgr.getImageCacheStores(scope);
if ((cacheStores == null) || (cacheStores.size() <= 0)) {


log.info("can t find staging storage in zone");
}
}

};