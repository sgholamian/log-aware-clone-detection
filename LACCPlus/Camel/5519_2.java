//,temp,RemoteFileConsumer.java,202,217,temp,RemoteFileConsumer.java,183,200
//,3
public class xxx {
    protected void disconnect() {
        // eager indicate we are no longer logged in
        loggedIn = false;

        // disconnect
        try {
            if (getOperations().isConnected()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Disconnecting from: {}", remoteServer());
                }
                getOperations().disconnect();
            }
        } catch (GenericFileOperationFailedException e) {
            // ignore just log a warning
            LOG.warn("Error occurred while disconnecting from {} due: {} This exception will be ignored.",
                    remoteServer(), e.getMessage());
        }
    }

};