//,temp,NiciraNvpConfigureSharedNetworkUuidCommandWrapper.java,163,171,temp,NiciraNvpConfigureSharedNetworkVlanIdCommandWrapper.java,95,103
//,2
public class xxx {
    private void cleanup(String logicalSwitchUuid, LogicalSwitchPort lSwitchPort, NiciraNvpApi niciraNvpApi) {
        s_logger.warn("Deleting previously created Logical Switch Port " + lSwitchPort.getUuid() + " (" + lSwitchPort.getDisplayName() + ") from Logical Switch " + logicalSwitchUuid);
        try {
            niciraNvpApi.deleteLogicalSwitchPort(logicalSwitchUuid, lSwitchPort.getUuid());
        } catch (NiciraNvpApiException exceptionDeleteLSwitchPort) {
            s_logger.error("Error while deleting Logical Switch Port " + lSwitchPort.getUuid() + " (" + lSwitchPort.getDisplayName() + ") from Logical Switch " + logicalSwitchUuid + " due to: " + exceptionDeleteLSwitchPort.getMessage());
        }
        s_logger.warn("Logical Switch Port " + lSwitchPort.getUuid() + " (" + lSwitchPort.getDisplayName() + ") successfully deteled");
    }

};