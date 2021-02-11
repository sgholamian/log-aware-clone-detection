//,temp,HostMO.java,1169,1186,temp,HypervisorHostHelper.java,937,953
//,3
public class xxx {
    public ManagedObjectReference waitForPortGroup(String networkName, long timeOutMs) throws Exception {
        ManagedObjectReference morNetwork = null;
        // if portGroup is just created, getNetwork may fail to retrieve it, we
        // need to retry
        long startTick = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTick <= timeOutMs) {
            morNetwork = getNetworkMor(networkName);
            if (morNetwork != null) {
                break;
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("Waiting for network " + networkName + " to be ready");
            }
            Thread.sleep(1000);
        }
        return morNetwork;
    }

};