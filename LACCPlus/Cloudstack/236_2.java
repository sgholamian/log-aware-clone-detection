//,temp,HostMO.java,1169,1186,temp,HypervisorHostHelper.java,937,953
//,3
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