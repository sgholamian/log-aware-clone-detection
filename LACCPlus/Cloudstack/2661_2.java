//,temp,CitrixOvsDeleteFlowCommandWrapper.java,40,63,temp,CitrixOvsSetTagAndFlowCommandWrapper.java,41,73
//,3
public class xxx {
    @Override
    public Answer execute(final OvsSetTagAndFlowCommand command, final CitrixResourceBase citrixResourceBase) {
        citrixResourceBase.setIsOvs(true);

        final Connection conn = citrixResourceBase.getConnection();
        try {
            final Network nw = citrixResourceBase.setupvSwitchNetwork(conn);
            final String bridge = nw.getBridge(conn);

            /*
             * If VM is domainRouter, this will try to set flow and tag on its
             * none guest network nic. don't worry, it will fail silently at
             * host plugin side
             */
            final String result = citrixResourceBase.callHostPlugin(conn, "ovsgre", "ovs_set_tag_and_flow", "bridge", bridge, "vmName", command.getVmName(), "tag",
                    command.getTag(), "vlans", command.getVlans(), "seqno", command.getSeqNo());
            s_logger.debug("set flow for " + command.getVmName() + " " + result);

            if (result != null && result.equalsIgnoreCase("SUCCESS")) {
                return new OvsSetTagAndFlowAnswer(command, true, result);
            } else {
                return new OvsSetTagAndFlowAnswer(command, false, result);
            }
        } catch (final BadServerResponse e) {
            s_logger.error("Failed to set tag and flow", e);
        } catch (final XenAPIException e) {
            s_logger.error("Failed to set tag and flow", e);
        } catch (final XmlRpcException e) {
            s_logger.error("Failed to set tag and flow", e);
        }

        return new OvsSetTagAndFlowAnswer(command, false, "EXCEPTION");
    }

};