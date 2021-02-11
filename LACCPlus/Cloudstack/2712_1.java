//,temp,CitrixOvsCreateTunnelCommandWrapper.java,38,68,temp,LibvirtOvsCreateTunnelCommandWrapper.java,37,65
//,3
public class xxx {
    @Override
    public Answer execute(final OvsCreateTunnelCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        String bridge = "unknown";
        try {
            final Network nw = citrixResourceBase.findOrCreateTunnelNetwork(conn, command.getNetworkName());
            if (nw == null) {
                s_logger.debug("Error during bridge setup");
                return new OvsCreateTunnelAnswer(command, false, "Cannot create network", bridge);
            }

            citrixResourceBase.configureTunnelNetwork(conn, command.getNetworkId(), command.getFrom(), command.getNetworkName());
            bridge = nw.getBridge(conn);
            final String result =
                    citrixResourceBase.callHostPlugin(conn, "ovstunnel", "create_tunnel", "bridge", bridge, "remote_ip", command.getRemoteIp(),
                            "key", command.getKey().toString(), "from",
                            command.getFrom().toString(), "to", command.getTo().toString(), "cloudstack-network-id",
                            command.getNetworkUuid());
            final String[] res = result.split(":");

            if (res.length == 2 && res[0].equalsIgnoreCase("SUCCESS")) {
                return new OvsCreateTunnelAnswer(command, true, result, res[1], bridge);
            } else {
                return new OvsCreateTunnelAnswer(command, false, result, bridge);
            }
        } catch (final Exception e) {
            s_logger.debug("Error during tunnel setup");
            s_logger.warn("Caught execption when creating ovs tunnel", e);
            return new OvsCreateTunnelAnswer(command, false, e.getMessage(), bridge);
        }
    }

};