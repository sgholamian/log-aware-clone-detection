//,temp,VmwareResource.java,5100,5106,temp,VmwareResource.java,844,851
//,2
public class xxx {
    protected Answer execute(CheckNetworkCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource CheckNetworkCommand " + _gson.toJson(cmd));
        }

        // TODO setup portgroup for private network needs to be done here now
        return new CheckNetworkAnswer(cmd, true, "Network Setup check by names is done");
    }

};