//,temp,HypervisorTemplateAdapter.java,399,444,temp,VolumeServiceImpl.java,1704,1762
//,3
public class xxx {
    protected Void registerVolumeCallback(AsyncCallbackDispatcher<VolumeServiceImpl, CreateCmdResult> callback, CreateVolumeContext<VolumeApiResult> context) {
        CreateCmdResult result = callback.getResult();
        VolumeObject vo = (VolumeObject)context.volume;
        try {
            if (result.isFailed()) {
                vo.processEvent(Event.OperationFailed);
                // delete the volume entry from volumes table in case of failure
                VolumeVO vol = volDao.findById(vo.getId());
                if (vol != null) {
                    volDao.remove(vo.getId());
                }

            } else {
                vo.processEvent(Event.OperationSuccessed, result.getAnswer());

                if (vo.getSize() != null) {
                    // publish usage events
                    // get physical size from volume_store_ref table
                    long physicalSize = 0;
                    DataStore ds = vo.getDataStore();
                    VolumeDataStoreVO volStore = _volumeStoreDao.findByStoreVolume(ds.getId(), vo.getId());
                    if (volStore != null) {
                        physicalSize = volStore.getPhysicalSize();
                    } else {
                        s_logger.warn("No entry found in volume_store_ref for volume id: " + vo.getId() + " and image store id: " + ds.getId() + " at the end of uploading volume!");
                    }
                    Scope dsScope = ds.getScope();
                    if (dsScope.getScopeType() == ScopeType.ZONE) {
                        if (dsScope.getScopeId() != null) {
                            UsageEventUtils.publishUsageEvent(EventTypes.EVENT_VOLUME_UPLOAD, vo.getAccountId(), dsScope.getScopeId(), vo.getId(), vo.getName(), null, null, physicalSize, vo.getSize(),
                                    Volume.class.getName(), vo.getUuid());
                        } else {
                            s_logger.warn("Zone scope image store " + ds.getId() + " has a null scope id");
                        }
                    } else if (dsScope.getScopeType() == ScopeType.REGION) {
                        // publish usage event for region-wide image store using a -1 zoneId for 4.2, need to revisit post-4.2
                        UsageEventUtils.publishUsageEvent(EventTypes.EVENT_VOLUME_UPLOAD, vo.getAccountId(), -1, vo.getId(), vo.getName(), null, null, physicalSize, vo.getSize(),
                                Volume.class.getName(), vo.getUuid());

                        _resourceLimitMgr.incrementResourceCount(vo.getAccountId(), ResourceType.secondary_storage, vo.getSize());
                    }
                }
            }
            VolumeApiResult res = new VolumeApiResult(vo);
            context.future.complete(res);
            return null;

        } catch (Exception e) {
            s_logger.error("register volume failed: ", e);
            // delete the volume entry from volumes table in case of failure
            VolumeVO vol = volDao.findById(vo.getId());
            if (vol != null) {
                volDao.remove(vo.getId());
            }
            VolumeApiResult res = new VolumeApiResult(null);
            context.future.complete(res);
            return null;
        }
    }

};