//,temp,SecondaryStorageManagerImpl.java,814,857,temp,ConsoleProxyManagerImpl.java,1003,1036
//,3
public class xxx {
    public boolean isZoneReady(Map<Long, ZoneHostInfo> zoneHostInfoMap, long dataCenterId) {
        ZoneHostInfo zoneHostInfo = zoneHostInfoMap.get(dataCenterId);
        if (zoneHostInfo != null && isZoneHostReady(zoneHostInfo)) {
            VMTemplateVO template = _templateDao.findSystemVMReadyTemplate(dataCenterId, HypervisorType.Any);
            if (template == null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("System vm template is not ready at data center " + dataCenterId + ", wait until it is ready to launch console proxy vm");
                }
                return false;
            }
            TemplateDataStoreVO templateHostRef = _vmTemplateStoreDao.findByTemplateZoneDownloadStatus(template.getId(), dataCenterId, Status.DOWNLOADED);

            if (templateHostRef != null) {
                boolean useLocalStorage = false;
                Boolean useLocal = ConfigurationManagerImpl.SystemVMUseLocalStorage.valueIn(dataCenterId);
                if (useLocal != null) {
                    useLocalStorage = useLocal.booleanValue();
                }
                List<Pair<Long, Integer>> l = _consoleProxyDao.getDatacenterStoragePoolHostInfo(dataCenterId, useLocalStorage);
                if (l != null && l.size() > 0 && l.get(0).second().intValue() > 0) {
                    return true;
                } else {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Primary storage is not ready, wait until it is ready to launch console proxy");
                    }
                }
            } else {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Zone host is ready, but console proxy template: " + template.getId() + " is not ready on secondary storage.");
                }
            }
        }
        return false;
    }

};