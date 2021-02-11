//,temp,NiciraNvpConfigureSharedNetworkUuidCommandWrapper.java,173,181,temp,NiciraNvpConfigureSharedNetworkUuidCommandWrapper.java,163,171
//,3
public class xxx {
    private void cleanupLRouterPort(String logicalRouterUuid, LogicalRouterPort lRouterPort, NiciraNvpApi niciraNvpApi) {
        s_logger.warn("Deleting previously created Logical Router Port " + lRouterPort.getUuid() + " (" + lRouterPort.getDisplayName() + ") from Logical Router " + logicalRouterUuid + " and retrying");
        try {
            niciraNvpApi.deleteLogicalRouterPort(logicalRouterUuid, lRouterPort.getUuid());
        } catch (NiciraNvpApiException exceptionDelete) {
            s_logger.error("Error while deleting Logical Router Port " + lRouterPort.getUuid() + " (" + lRouterPort.getDisplayName() + ") from Logical Router " + logicalRouterUuid + " due to: " + exceptionDelete.getMessage());
        }
        s_logger.warn("Logical Router Port " + lRouterPort.getUuid() + " (" + lRouterPort.getDisplayName() + ") successfully deleted");
    }

};