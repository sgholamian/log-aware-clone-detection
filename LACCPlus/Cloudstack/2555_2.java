//,temp,LibvirtOvsVpcPhysicalTopologyConfigCommandWrapper.java,36,54,temp,LibvirtOvsDestroyTunnelCommandWrapper.java,36,59
//,3
public class xxx {
    @Override
    public Answer execute(final OvsDestroyTunnelCommand command, final LibvirtComputingResource libvirtComputingResource) {
        try {
            if (!libvirtComputingResource.findOrCreateTunnelNetwork(command.getBridgeName())) {
                s_logger.warn("Unable to find tunnel network for GRE key:"
                        + command.getBridgeName());
                return new Answer(command, false, "No network found");
            }

            final Script scriptCommand = new Script(libvirtComputingResource.getOvsTunnelPath(), libvirtComputingResource.getTimeout(), s_logger);
            scriptCommand.add("destroy_tunnel");
            scriptCommand.add("--bridge", command.getBridgeName());
            scriptCommand.add("--iface_name", command.getInPortName());
            final String result = scriptCommand.execute();
            if (result == null) {
                return new Answer(command, true, result);
            } else {
                return new Answer(command, false, result);
            }
        } catch (final Exception e) {
            s_logger.warn("caught execption when destroy ovs tunnel", e);
            return new Answer(command, false, e.getMessage());
        }
    }

};