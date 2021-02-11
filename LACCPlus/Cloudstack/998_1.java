//,temp,CitrixOvsSetupBridgeCommandWrapper.java,36,46,temp,LibvirtOvsSetupBridgeCommandWrapper.java,35,48
//,3
public class xxx {
    @Override
    public Answer execute(final OvsSetupBridgeCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();

        citrixResourceBase.findOrCreateTunnelNetwork(conn, command.getBridgeName());
        citrixResourceBase.configureTunnelNetwork(conn, command.getNetworkId(), command.getHostId(), command.getBridgeName());

        s_logger.debug("OVS Bridge configured");

        return new Answer(command, true, null);
    }

};