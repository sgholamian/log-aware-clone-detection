//,temp,HypervisorHostHelper.java,1421,1439,temp,HypervisorHostHelper.java,937,953
//,2
public class xxx {
    public static ManagedObjectReference waitForNetworkReady(HostMO hostMo, String networkName, long timeOutMs) throws Exception {

        ManagedObjectReference morNetwork = null;

        // if portGroup is just created, getNetwork may fail to retrieve it, we
        // need to retry
        long startTick = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTick <= timeOutMs) {
            morNetwork = hostMo.getNetworkMor(networkName);
            if (morNetwork != null) {
                break;
            }

            s_logger.info("Waiting for network " + networkName + " to be ready");
            Thread.sleep(1000);
        }

        return morNetwork;
    }

};