//,temp,VmwareResource.java,5040,5046,temp,VmwareResource.java,844,851
//,3
public class xxx {
    protected Answer execute(CheckNetworkCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CheckNetworkCommand " + _gson.toJson(cmd));
        }

        // TODO setup portgroup for private network needs to be done here now
        return new CheckNetworkAnswer(cmd, true, "Network Setup check by names is done");
    }

};