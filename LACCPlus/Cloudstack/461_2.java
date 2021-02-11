//,temp,SecondaryStorageManagerImpl.java,1077,1098,temp,ConsoleProxyManagerImpl.java,1211,1235
//,3
public class xxx {
    @Override
    public boolean destroyProxy(long vmId) {
        ConsoleProxyVO proxy = _consoleProxyDao.findById(vmId);
        try {
            //expunge the vm
            _itMgr.expunge(proxy.getUuid());
            proxy.setPublicIpAddress(null);
            proxy.setPublicMacAddress(null);
            proxy.setPublicNetmask(null);
            proxy.setPrivateMacAddress(null);
            proxy.setPrivateIpAddress(null);
            _consoleProxyDao.update(proxy.getId(), proxy);
            _consoleProxyDao.remove(vmId);
            HostVO host = _hostDao.findByTypeNameAndZoneId(proxy.getDataCenterId(), proxy.getHostName(), Host.Type.ConsoleProxy);
            if (host != null) {
                s_logger.debug("Removing host entry for proxy id=" + vmId);
                return _hostDao.remove(host.getId());
            }

            return true;
        } catch (ResourceUnavailableException e) {
            s_logger.warn("Unable to expunge " + proxy, e);
            return false;
        }
    }

};