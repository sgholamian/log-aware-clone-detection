//,temp,CitrixOvsVpcPhysicalTopologyConfigCommandWrapper.java,37,58,temp,CitrixOvsVpcRoutingPolicyConfigCommandWrapper.java,37,58
//,2
public class xxx {
    @Override
    public Answer execute(final OvsVpcPhysicalTopologyConfigCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        try {
            final Network nw = citrixResourceBase.findOrCreateTunnelNetwork(conn, command.getBridgeName());
            final String bridgeName = nw.getBridge(conn);
            final long sequenceNo = command.getSequenceNumber();

            final String result = citrixResourceBase.callHostPlugin(conn, "ovstunnel", "configure_ovs_bridge_for_network_topology", "bridge",
                    bridgeName, "config", command.getVpcConfigInJson(), "host-id", ((Long)command.getHostId()).toString(),
                    "seq-no", Long.toString(sequenceNo));

            if (result.startsWith("SUCCESS")) {
                return new Answer(command, true, result);
            } else {
                return new Answer(command, false, result);
            }
        } catch  (final Exception e) {
            s_logger.warn("caught exception while updating host with latest VPC topology", e);
            return new Answer(command, false, e.getMessage());
        }
    }

};