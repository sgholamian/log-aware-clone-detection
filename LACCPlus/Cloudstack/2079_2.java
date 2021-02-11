//,temp,LibvirtPlugNicCommandWrapper.java,44,85,temp,LibvirtUnPlugNicCommandWrapper.java,44,81
//,3
public class xxx {
    @Override
    public Answer execute(final UnPlugNicCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final NicTO nic = command.getNic();
        final String vmName = command.getVmName();
        Domain vm = null;
        try {
            final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();

            final Connect conn = libvirtUtilitiesHelper.getConnectionByVmName(vmName);
            vm = libvirtComputingResource.getDomain(conn, vmName);
            final List<InterfaceDef> pluggedNics = libvirtComputingResource.getInterfaces(conn, vmName);

            for (final InterfaceDef pluggedNic : pluggedNics) {
                if (pluggedNic.getMacAddress().equalsIgnoreCase(nic.getMac())) {
                    vm.detachDevice(pluggedNic.toString());
                    // We don't know which "traffic type" is associated with
                    // each interface at this point, so inform all vif drivers
                    for (final VifDriver vifDriver : libvirtComputingResource.getAllVifDrivers()) {
                        vifDriver.unplug(pluggedNic);
                    }
                    return new UnPlugNicAnswer(command, true, "success");
                }
            }
            return new UnPlugNicAnswer(command, true, "success");
        } catch (final LibvirtException e) {
            final String msg = " Unplug Nic failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new UnPlugNicAnswer(command, false, msg);
        } finally {
            if (vm != null) {
                try {
                    vm.free();
                } catch (final LibvirtException l) {
                    s_logger.trace("Ignoring libvirt error.", l);
                }
            }
        }
    }

};