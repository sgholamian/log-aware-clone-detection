//,temp,SnapshotDataStoreDaoImpl.java,432,445,temp,TemplateDataStoreDaoImpl.java,513,527
//,2
public class xxx {
    @Override
    public void updateStoreRoleToCache(long storeId) {
        SearchCriteria<SnapshotDataStoreVO> sc = storeSearch.create();
        sc.setParameters("store_id", storeId);
        sc.setParameters("destroyed", false);
        List<SnapshotDataStoreVO> snaps = listBy(sc);
        if (snaps != null) {
            s_logger.info("Update to cache store role for " + snaps.size() + " entries in snapshot_store_ref");
            for (SnapshotDataStoreVO snap : snaps) {
                snap.setRole(DataStoreRole.ImageCache);
                update(snap.getId(), snap);
            }
        }
    }

};