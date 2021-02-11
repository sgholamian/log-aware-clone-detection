//,temp,SolidFireHostListener.java,71,87,temp,SolidFireSharedHostListener.java,65,81
//,2
public class xxx {
    @Override
    public boolean hostAdded(long hostId) {
        HostVO host = hostDao.findById(hostId);

        if (host == null) {
            LOGGER.error("Failed to add host by SolidFireHostListener as host was not found with id = " + hostId);

            return false;
        }

        SolidFireUtil.hostAddedToCluster(hostId, host.getClusterId(), SolidFireUtil.PROVIDER_NAME,
                clusterDao, hostDao, storagePoolDao, storagePoolDetailsDao);

        handleVMware(host, true, ModifyTargetsCommand.TargetTypeToRemove.NEITHER);

        return true;
    }

};