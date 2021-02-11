//,temp,VmwareResource.java,4928,4947,temp,VmwareResource.java,3517,3536
//,3
public class xxx {
    protected Answer execute(CheckHealthCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CheckHealthCommand: " + _gson.toJson(cmd));
        }

        try {
            VmwareHypervisorHost hyperHost = getHyperHost(getServiceContext());
            if (hyperHost.isHyperHostConnected()) {
                return new CheckHealthAnswer(cmd, true);
            }
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            s_logger.error("Unable to execute CheckHealthCommand due to " + VmwareHelper.getExceptionMessage(e), e);
        }
        return new CheckHealthAnswer(cmd, false);
    }

};