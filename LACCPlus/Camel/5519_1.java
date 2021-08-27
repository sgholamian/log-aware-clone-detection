//,temp,RemoteFileConsumer.java,202,217,temp,RemoteFileConsumer.java,183,200
//,3
public class xxx {
    protected void forceDisconnect() {
        // eager indicate we are no longer logged in
        loggedIn = false;

        // disconnect
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Force disconnecting from: {}", remoteServer());
            }
            getOperations().forceDisconnect();
        } catch (GenericFileOperationFailedException e) {
            // ignore just log a warning
            LOG.warn("Error occurred while disconnecting from {} due: {} This exception will be ignored.",
                    remoteServer(), e.getMessage());
        }
    }

};