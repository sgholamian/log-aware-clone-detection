//,temp,SecondaryStorageManagerImpl.java,814,857,temp,ConsoleProxyManagerImpl.java,1003,1036
//,3
public class xxx {
    public boolean isZoneReady(Map<Long, ZoneHostInfo> zoneHostInfoMap, long dataCenterId) {
        ZoneHostInfo zoneHostInfo = zoneHostInfoMap.get(dataCenterId);
        if (zoneHostInfo != null && (zoneHostInfo.getFlags() & RunningHostInfoAgregator.ZoneHostInfo.ROUTING_HOST_MASK) != 0) {
            VMTemplateVO template = _templateDao.findSystemVMReadyTemplate(dataCenterId, HypervisorType.Any);
            if (template == null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("System vm template is not ready at data center " + dataCenterId + ", wait until it is ready to launch secondary storage vm");
                }
                return false;
            }

            List<DataStore> stores = _dataStoreMgr.getImageStoresByScope(new ZoneScope(dataCenterId));
            if (stores.size() < 1) {
                s_logger.debug("No image store added  in zone " + dataCenterId + ", wait until it is ready to launch secondary storage vm");
                return false;
            }

            DataStore store = templateMgr.getImageStore(dataCenterId, template.getId());
            if (store == null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("No secondary storage available in zone " + dataCenterId + ", wait until it is ready to launch secondary storage vm");
                }
                return false;
            }

            boolean useLocalStorage = false;
            Boolean useLocal = ConfigurationManagerImpl.SystemVMUseLocalStorage.valueIn(dataCenterId);
            if (useLocal != null) {
                useLocalStorage = useLocal.booleanValue();
            }
            List<Pair<Long, Integer>> l = _storagePoolHostDao.getDatacenterStoragePoolHostInfo(dataCenterId, !useLocalStorage);
            if (l != null && l.size() > 0 && l.get(0).second().intValue() > 0) {
                return true;
            } else {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Primary storage is not ready, wait until it is ready to launch secondary storage vm. dcId: " + dataCenterId +
                        ", " + ConfigurationManagerImpl.SystemVMUseLocalStorage.key() + ": " + useLocalStorage + ". " +
                        "If you want to use local storage to start SSVM, need to set " + ConfigurationManagerImpl.SystemVMUseLocalStorage.key() + " to true");
                }
            }

        }
        return false;
    }

};