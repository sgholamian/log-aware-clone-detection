//,temp,SnapshotDataStoreDaoImpl.java,432,445,temp,TemplateDataStoreDaoImpl.java,513,527
//,2
public class xxx {
    @Override
    public void updateStoreRoleToCachce(long storeId) {
        SearchCriteria<TemplateDataStoreVO> sc = storeSearch.create();
        sc.setParameters("store_id", storeId);
        sc.setParameters("destroyed", false);
        List<TemplateDataStoreVO> tmpls = listBy(sc);
        if (tmpls != null) {
            s_logger.info("Update to cache store role for " + tmpls.size() + " entries in template_store_ref");
            for (TemplateDataStoreVO tmpl : tmpls) {
                tmpl.setDataStoreRole(DataStoreRole.ImageCache);
                update(tmpl.getId(), tmpl);
            }
        }

    }

};