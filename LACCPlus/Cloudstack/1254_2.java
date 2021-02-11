//,temp,Ovm3VmSupport.java,129,152,temp,Ovm3VmSupport.java,108,128
//,3
public class xxx {
    private Boolean createVif(Xen.Vm vm, NicTO nic)
            throws Ovm3ResourceException {
        try {
            String net = network.getNetwork(nic);
            if (net != null) {
                LOGGER.debug("Adding vif " + nic.getDeviceId() + " "
                        + nic.getMac() + " " + net + " to " + vm.getVmName());
                vm.addVif(nic.getDeviceId(), net, nic.getMac());
            } else {
                LOGGER.debug("Unable to add vif " + nic.getDeviceId()
                        + " no network for " + vm.getVmName());
                return false;
            }
        } catch (Exception e) {
            String msg = "Unable to add vif " + nic.getType() + " for "
                    + vm.getVmName() + " " + e.getMessage();
            LOGGER.debug(msg);
            throw new Ovm3ResourceException(msg);
        }
        return true;
    }

};