//,temp,VmwareResource.java,3517,3536,temp,VmwareResource.java,3377,3394
//,3
public class xxx {
    protected Answer execute(ReadyCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource ReadyCommand: " + _gson.toJson(cmd));
        }

        try {
            VmwareContext context = getServiceContext();
            VmwareHypervisorHost hyperHost = getHyperHost(context);
            if (hyperHost.isHyperHostConnected()) {
                return new ReadyAnswer(cmd);
            } else {
                return new ReadyAnswer(cmd, "Host is not in connect state");
            }
        } catch (Exception e) {
            s_logger.error("Unexpected exception: ", e);
            return new ReadyAnswer(cmd, VmwareHelper.getExceptionMessage(e));
        }
    }

};