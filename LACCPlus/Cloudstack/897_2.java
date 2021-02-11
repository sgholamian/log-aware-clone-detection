//,temp,Ovm3HypervisorSupport.java,554,571,temp,Ovm3HypervisorSupport.java,331,341
//,3
public class xxx {
    private Map<String, Xen.Vm> getAllVms() throws Ovm3ResourceException {
        try {
            Xen vms = new Xen(c);
            return vms.getRunningVmConfigs();
        } catch (Exception e) {
            LOGGER.debug("getting VM list from " + config.getAgentHostname()
                    + " failed", e);
            throw new CloudRuntimeException("Exception on getting VMs from "
                    + config.getAgentHostname() + ":" + e.getMessage(), e);
        }
    }

};