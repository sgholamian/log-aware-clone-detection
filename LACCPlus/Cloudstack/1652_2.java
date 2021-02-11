//,temp,Ovm3HypervisorSupport.java,718,727,temp,OvmResourceBase.java,903,911
//,3
public class xxx {
    protected FenceAnswer execute(FenceCommand cmd) {
        try {
            Boolean res = OvmHost.fence(_conn, cmd.getHostIp());
            return new FenceAnswer(cmd, res, res.toString());
        } catch (Exception e) {
            s_logger.debug("fence " + cmd.getHostIp() + " failed", e);
            return new FenceAnswer(cmd, false, e.getMessage());
        }
    }

};