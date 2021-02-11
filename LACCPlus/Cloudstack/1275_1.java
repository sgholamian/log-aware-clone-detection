//,temp,NiciraNvpFindL2GatewayServiceCommandWrapper.java,45,67,temp,NiciraNvpFindLogicalRouterPortCommandWrapper.java,44,66
//,3
public class xxx {
    @Override
    public Answer execute(FindL2GatewayServiceCommand command, NiciraNvpResource niciraNvpResource) {
        final GatewayServiceConfig config = command.getGatewayServiceConfig();
        final String uuid = config.getUuid();
        final String type = config.getType();
        final NiciraNvpApi niciraNvpApi = niciraNvpResource.getNiciraNvpApi();

        s_logger.info("Looking for L2 Gateway Service " + uuid + " of type " + type);

        try {
            List<L2GatewayServiceConfig> lstGW = niciraNvpApi.findL2GatewayServiceByUuidAndType(uuid, type);
            if (lstGW.size() == 0) {
                return new FindL2GatewayServiceAnswer(command, false, "L2 Gateway Service not found", null);
            } else {
                return new FindL2GatewayServiceAnswer(command, true, "L2 Gateway Service " + lstGW.get(0).getDisplayName()+ " found", lstGW.get(0).getUuid());
            }
        } catch (NiciraNvpApiException e) {
            s_logger.error("Error finding Gateway Service due to: " + e.getMessage());
            final CommandRetryUtility retryUtility = niciraNvpResource.getRetryUtility();
            retryUtility.addRetry(command, NUM_RETRIES);
            return retryUtility.retry(command, FindL2GatewayServiceAnswer.class, e);
        }
    }

};