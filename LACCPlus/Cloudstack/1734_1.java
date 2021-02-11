//,temp,CitrixOvsDestroyBridgeCommandWrapper.java,37,54,temp,CitrixOvsSetupBridgeCommandWrapper.java,36,46
//,3
public class xxx {
    @Override
    public Answer execute(final OvsDestroyBridgeCommand command, final CitrixResourceBase citrixResourceBase) {
        try {
            final Connection conn = citrixResourceBase.getConnection();

            final Network nw = citrixResourceBase.findOrCreateTunnelNetwork(conn, command.getBridgeName());
            citrixResourceBase.cleanUpTmpDomVif(conn, nw);

            citrixResourceBase.destroyTunnelNetwork(conn, nw, command.getHostId());

            s_logger.debug("OVS Bridge destroyed");

            return new Answer(command, true, null);
        } catch (final Exception e) {
            s_logger.warn("caught execption when destroying ovs bridge", e);
            return new Answer(command, false, e.getMessage());
        }
    }

};