//,temp,CitrixOvsCreateTunnelCommandWrapper.java,38,68,temp,LibvirtOvsCreateTunnelCommandWrapper.java,37,65
//,3
public class xxx {
    @Override
    public Answer execute(final OvsCreateTunnelCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final String bridge = command.getNetworkName();
        try {
            if (!libvirtComputingResource.findOrCreateTunnelNetwork(bridge)) {
                s_logger.debug("Error during bridge setup");
                return new OvsCreateTunnelAnswer(command, false, "Cannot create network", bridge);
            }

            libvirtComputingResource.configureTunnelNetwork(command.getNetworkId(), command.getFrom(), command.getNetworkName());
            final Script scriptCommand = new Script(libvirtComputingResource.getOvsTunnelPath(), libvirtComputingResource.getTimeout(), s_logger);
            scriptCommand.add("create_tunnel");
            scriptCommand.add("--bridge", bridge);
            scriptCommand.add("--remote_ip", command.getRemoteIp());
            scriptCommand.add("--key", command.getKey().toString());
            scriptCommand.add("--src_host", command.getFrom().toString());
            scriptCommand.add("--dst_host", command.getTo().toString());

            final String result = scriptCommand.execute();
            if (result != null) {
                return new OvsCreateTunnelAnswer(command, true, result, null, bridge);
            } else {
                return new OvsCreateTunnelAnswer(command, false, result, bridge);
            }
        } catch (final Exception e) {
            s_logger.warn("Caught execption when creating ovs tunnel", e);
            return new OvsCreateTunnelAnswer(command, false, e.getMessage(), bridge);
        }
    }

};