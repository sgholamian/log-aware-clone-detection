//,temp,LibvirtPlugNicCommandWrapper.java,44,85,temp,LibvirtUnPlugNicCommandWrapper.java,44,81
//,3
public class xxx {
    @Override
    public Answer execute(final PlugNicCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final NicTO nic = command.getNic();
        final String vmName = command.getVmName();
        Domain vm = null;
        try {
            final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();
            final Connect conn = libvirtUtilitiesHelper.getConnectionByVmName(vmName);
            vm = libvirtComputingResource.getDomain(conn, vmName);

            final List<InterfaceDef> pluggedNics = libvirtComputingResource.getInterfaces(conn, vmName);
            Integer nicnum = 0;
            for (final InterfaceDef pluggedNic : pluggedNics) {
                if (pluggedNic.getMacAddress().equalsIgnoreCase(nic.getMac())) {
                    s_logger.debug("found existing nic for mac " + pluggedNic.getMacAddress() + " at index " + nicnum);
                    return new PlugNicAnswer(command, true, "success");
                }
                nicnum++;
            }
            final VifDriver vifDriver = libvirtComputingResource.getVifDriver(nic.getType(), nic.getName());
            final InterfaceDef interfaceDef = vifDriver.plug(nic, "Other PV", "", null);
            vm.attachDevice(interfaceDef.toString());

            return new PlugNicAnswer(command, true, "success");
        } catch (final LibvirtException e) {
            final String msg = " Plug Nic failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new PlugNicAnswer(command, false, msg);
        } catch (final InternalErrorException e) {
            final String msg = " Plug Nic failed due to " + e.toString();
            s_logger.warn(msg, e);
            return new PlugNicAnswer(command, false, msg);
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