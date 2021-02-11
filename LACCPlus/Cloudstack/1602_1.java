//,temp,VmwareResource.java,5308,5328,temp,VmwareResource.java,4875,4899
//,3
public class xxx {
    @Override
    public CopyVolumeAnswer execute(CopyVolumeCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CopyVolumeCommand: " + _gson.toJson(cmd));
        }

        try {
            VmwareContext context = getServiceContext();
            VmwareManager mgr = context.getStockObject(VmwareManager.CONTEXT_STOCK_NAME);
            return (CopyVolumeAnswer)mgr.getStorageManager().execute(this, cmd);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            String msg = "CopyVolumeCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.error(msg, e);
            return new CopyVolumeAnswer(cmd, false, msg, null, null);
        }
    }

};