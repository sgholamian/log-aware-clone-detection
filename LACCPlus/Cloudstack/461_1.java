//,temp,SecondaryStorageManagerImpl.java,1077,1098,temp,ConsoleProxyManagerImpl.java,1211,1235
//,3
public class xxx {
    @Override
    public boolean destroySecStorageVm(long vmId) {
        SecondaryStorageVmVO ssvm = _secStorageVmDao.findById(vmId);

        try {
            _itMgr.expunge(ssvm.getUuid());
            _secStorageVmDao.remove(ssvm.getId());
            HostVO host = _hostDao.findByTypeNameAndZoneId(ssvm.getDataCenterId(), ssvm.getHostName(), Host.Type.SecondaryStorageVM);
            if (host != null) {
                s_logger.debug("Removing host entry for ssvm id=" + vmId);
                _hostDao.remove(host.getId());
                //Expire the download urls in the entire zone for templates and volumes.
                _tmplStoreDao.expireDnldUrlsForZone(host.getDataCenterId());
                _volumeStoreDao.expireDnldUrlsForZone(host.getDataCenterId());
                return true;
            }
            return false;
        } catch (ResourceUnavailableException e) {
            s_logger.warn("Unable to expunge " + ssvm, e);
            return false;
        }
    }

};