//,temp,HypervisorHostHelper.java,1421,1439,temp,HypervisorHostHelper.java,937,953
//,2
public class xxx {
    public static ManagedObjectReference waitForDvPortGroupReady(DatacenterMO dataCenterMo, String dvPortGroupName, long timeOutMs) throws Exception {
        ManagedObjectReference morDvPortGroup = null;

        // if DvPortGroup is just created, we may fail to retrieve it, we
        // need to retry
        long startTick = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTick <= timeOutMs) {
            morDvPortGroup = dataCenterMo.getDvPortGroupMor(dvPortGroupName);
            if (morDvPortGroup != null) {
                break;
            }

            s_logger.info("Waiting for dvPortGroup " + dvPortGroupName + " to be ready");
            Thread.sleep(1000);
        }
        return morDvPortGroup;
    }

};