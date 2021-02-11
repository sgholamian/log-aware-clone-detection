//,temp,LibvirtOvsVpcPhysicalTopologyConfigCommandWrapper.java,36,54,temp,LibvirtOvsDestroyTunnelCommandWrapper.java,36,59
//,3
public class xxx {
    @Override
    public Answer execute(final OvsVpcPhysicalTopologyConfigCommand command, final LibvirtComputingResource libvirtComputingResource) {
        try {
            final Script scriptCommand = new Script(libvirtComputingResource.getOvsTunnelPath(), libvirtComputingResource.getTimeout(), s_logger);
            scriptCommand.add("configure_ovs_bridge_for_network_topology");
            scriptCommand.add("--bridge", command.getBridgeName());
            scriptCommand.add("--config", command.getVpcConfigInJson());

            final String result = scriptCommand.execute();
            if (result.equalsIgnoreCase("SUCCESS")) {
                return new Answer(command, true, result);
            } else {
                return new Answer(command, false, result);
            }
        } catch  (final Exception e) {
            s_logger.warn("caught exception while updating host with latest routing polcies", e);
            return new Answer(command, false, e.getMessage());
        }
    }

};