//,temp,AncientDataMotionStrategy.java,133,146,temp,StorageSystemDataMotionStrategy.java,625,640
//,3
public class xxx {
    private Scope pickCacheScopeForCopy(DataObject srcData, DataObject destData) {
        Scope srcScope = srcData.getDataStore().getScope();
        Scope destScope = destData.getDataStore().getScope();

        Scope selectedScope = null;
        if (srcScope.getScopeId() != null) {
            selectedScope = getZoneScope(srcScope);
        } else if (destScope.getScopeId() != null) {
            selectedScope = getZoneScope(destScope);
        } else {
            s_logger.warn("Cannot find a zone-wide scope for movement that needs a cache storage");
        }
        return selectedScope;
    }

};