//,temp,Ovm3HypervisorSupport.java,718,727,temp,OvmResourceBase.java,903,911
//,3
public class xxx {
    public FenceAnswer execute(FenceCommand cmd) {
        LOGGER.debug("FenceCommand");
        try {
            Boolean res = false;
            return new FenceAnswer(cmd, res, res.toString());
        } catch (Exception e) {
            LOGGER.error("Unable to fence" + cmd.getHostIp(), e);
            return new FenceAnswer(cmd, false, e.getMessage());
        }
    }

};