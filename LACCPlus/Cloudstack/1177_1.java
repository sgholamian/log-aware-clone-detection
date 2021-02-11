//,temp,TemplateServiceImpl.java,992,1023,temp,SnapshotServiceImpl.java,497,526
//,3
public class xxx {
    @Override
    public void syncTemplateToRegionStore(long templateId, DataStore store) {
        if (_storeMgr.isRegionStore(store)) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Sync template " + templateId + " from cache to object store...");
            }
            // if template is on region wide object store, check if it is really downloaded there (by checking install_path). Sync template to region
            // wide store if it is not there physically.
            TemplateInfo tmplOnStore = _templateFactory.getTemplate(templateId, store);
            if (tmplOnStore == null) {
                throw new CloudRuntimeException("Cannot find an entry in template_store_ref for template " + templateId + " on region store: " + store.getName());
            }
            if (tmplOnStore.getInstallPath() == null || tmplOnStore.getInstallPath().length() == 0) {
                // template is not on region store yet, sync to region store
                TemplateInfo srcTemplate = _templateFactory.getReadyTemplateOnCache(templateId);
                if (srcTemplate == null) {
                    throw new CloudRuntimeException("Cannot find template " + templateId + "  on cache store");
                }
                AsyncCallFuture<TemplateApiResult> future = syncToRegionStoreAsync(srcTemplate, store);
                try {
                    TemplateApiResult result = future.get();
                    if (result.isFailed()) {
                        throw new CloudRuntimeException("sync template from cache to region wide store failed for image store " + store.getName() + ":" +
                                result.getResult());
                    }
                    _cacheMgr.releaseCacheObject(srcTemplate); // reduce reference count for template on cache, so it can recycled by schedule
                } catch (Exception ex) {
                    throw new CloudRuntimeException("sync template from cache to region wide store failed for image store " + store.getName());
                }
            }
        }
    }

};