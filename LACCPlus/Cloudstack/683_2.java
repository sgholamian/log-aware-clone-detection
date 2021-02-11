//,temp,NiciraNvpConfigureSharedNetworkUuidCommandWrapper.java,183,194,temp,NiciraNvpConfigureSharedNetworkVlanIdCommandWrapper.java,105,116
//,2
public class xxx {
    private Answer handleException(NiciraNvpApiException e, ConfigureSharedNetworkVlanIdCommand command, NiciraNvpResource niciraNvpResource) {
        if (HttpStatusCodeHelper.isConflict(e.getErrorCode())){
            s_logger.warn("There's been a conflict in NSX side, aborting implementation");
            return new ConfigureSharedNetworkVlanIdAnswer(command, false, "FAILED: There's been a conflict in NSX side");
        }
        else {
            s_logger.warn("Error code: " + e.getErrorCode() + ", retrying");
            final CommandRetryUtility retryUtility = niciraNvpResource.getRetryUtility();
            retryUtility.addRetry(command, NUM_RETRIES);
            return retryUtility.retry(command, ConfigureSharedNetworkVlanIdAnswer.class, e);
        }
    }

};