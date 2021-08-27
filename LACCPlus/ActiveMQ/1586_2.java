//,temp,SimpleConnectionManager.java,91,103,temp,ConnectionManagerAdapter.java,116,130
//,3
public class xxx {
    @Override
    public void connectionErrorOccurred(ConnectionEvent event) {
        LOG.warn("Managed connection experiened an error: ", event.getException());
        try {
            ((ManagedConnection)event.getSource()).cleanup();
        } catch (ResourceException e) {
            LOG.warn("Error occured during the cleanup of a managed connection: ", e);
        }

        try {
            ((ManagedConnection)event.getSource()).destroy();
        } catch (ResourceException e) {
            LOG.warn("Error occured during the destruction of a managed connection: ", e);
        }
    }

};