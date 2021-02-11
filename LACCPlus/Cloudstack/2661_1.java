//,temp,CitrixOvsDeleteFlowCommandWrapper.java,40,63,temp,CitrixOvsSetTagAndFlowCommandWrapper.java,41,73
//,3
public class xxx {
    @Override
    public Answer execute(final OvsDeleteFlowCommand command, final CitrixResourceBase citrixResourceBase) {
        citrixResourceBase.setIsOvs(true);

        final Connection conn = citrixResourceBase.getConnection();
        try {
            final Network nw = citrixResourceBase.setupvSwitchNetwork(conn);
            final String bridge = nw.getBridge(conn);
            final String result = citrixResourceBase.callHostPlugin(conn, "ovsgre", "ovs_delete_flow", "bridge", bridge, "vmName", command.getVmName());

            if (result.equalsIgnoreCase("SUCCESS")) {
                return new Answer(command, true, "success to delete flows for " + command.getVmName());
            } else {
                return new Answer(command, false, result);
            }
        } catch (final BadServerResponse e) {
            s_logger.error("Failed to delete flow", e);
        } catch (final XenAPIException e) {
            s_logger.error("Failed to delete flow", e);
        } catch (final XmlRpcException e) {
            s_logger.error("Failed to delete flow", e);
        }
        return new Answer(command, false, "failed to delete flow for " + command.getVmName());
    }

};