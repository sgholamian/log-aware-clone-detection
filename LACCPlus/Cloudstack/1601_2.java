//,temp,VmwareResource.java,5170,5190,temp,VmwareResource.java,4875,4899
//,3
public class xxx {
    protected Answer execute(CreateVolumeFromSnapshotCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CreateVolumeFromSnapshotCommand: " + _gson.toJson(cmd));
        }

        String details = null;
        boolean success = false;
        String newVolumeName = UUID.randomUUID().toString();

        try {
            VmwareContext context = getServiceContext();
            VmwareManager mgr = context.getStockObject(VmwareManager.CONTEXT_STOCK_NAME);
            return mgr.getStorageManager().execute(this, cmd);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            details = "CreateVolumeFromSnapshotCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.error(details, e);
        }

        return new CreateVolumeFromSnapshotAnswer(cmd, success, details, newVolumeName);
    }

};