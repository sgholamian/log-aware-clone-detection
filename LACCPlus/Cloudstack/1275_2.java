//,temp,NiciraNvpFindL2GatewayServiceCommandWrapper.java,45,67,temp,NiciraNvpFindLogicalRouterPortCommandWrapper.java,44,66
//,3
public class xxx {
    @Override
    public Answer execute(FindLogicalRouterPortCommand command, NiciraNvpResource niciraNvpResource) {
        final String logicalRouterUuid = command.getLogicalRouterUuid();
        final String attachmentLswitchUuid = command.getAttachmentLswitchUuid();
        final NiciraNvpApi niciraNvpApi = niciraNvpResource.getNiciraNvpApi();

        s_logger.debug("Finding Logical Router Port in Logical Router " + logicalRouterUuid + " and attachmentLSwitchUuid " + attachmentLswitchUuid);

        try{
            List<LogicalRouterPort> lRouterPorts = niciraNvpApi.findLogicalRouterPortByAttachmentLSwitchUuid(logicalRouterUuid, attachmentLswitchUuid);
            if (lRouterPorts.size() == 0) {
                return new FindLogicalRouterPortAnswer(command, false, "Logical Router Port not found", null);
            } else {
                return new FindLogicalRouterPortAnswer(command, true, "Logical Router Port found", lRouterPorts.get(0).getUuid());
            }
        }
        catch (NiciraNvpApiException e){
            s_logger.error("Error finding Logical Router Port due to: " + e.getMessage());
            final CommandRetryUtility retryUtility = niciraNvpResource.getRetryUtility();
            retryUtility.addRetry(command, NUM_RETRIES);
            return retryUtility.retry(command, FindLogicalRouterPortAnswer.class, e);
        }
    }

};