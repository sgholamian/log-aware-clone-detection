//,temp,Ovm3VmSupport.java,129,152,temp,Ovm3VmSupport.java,108,128
//,3
public class xxx {
    private Boolean deleteVif(Xen.Vm vm, NicTO nic)
            throws Ovm3ResourceException {
        /* here we should use the housekeeping of VLANs/Networks etc..
         * so we can clean after the last VM is gone
         */
        try {
            String net = network.getNetwork(nic);
            if (net != null) {
                LOGGER.debug("Removing vif " + nic.getDeviceId() + " " + " "
                        + nic.getMac() + " " + net + " from " + vm.getVmName());
                vm.removeVif(net, nic.getMac());
            } else {
                LOGGER.debug("Unable to remove vif " + nic.getDeviceId()
                        + " no network for " + vm.getVmName());
                return false;
            }
        } catch (Exception e) {
            String msg = "Unable to remove vif " + nic.getType() + " for "
                    + vm.getVmName() + " " + e.getMessage();
            LOGGER.debug(msg);
            throw new Ovm3ResourceException(msg);
        }
        return true;
    }

};