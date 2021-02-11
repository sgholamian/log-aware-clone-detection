//,temp,NetworkUsageManagerImpl.java,478,484,temp,MockAgentManagerImpl.java,517,523
//,3
public class xxx {
    @Override
    public Answer checkNetworkCommand(CheckNetworkCommand cmd) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Checking if network name setup is done on the resource");
        }
        return new CheckNetworkAnswer(cmd, true, "Network Setup check by names is done");
    }

};