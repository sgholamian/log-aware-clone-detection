//,temp,SimpleConnectionManager.java,57,68,temp,ConnectionManagerAdapter.java,80,90
//,3
public class xxx {
    public void connectionClosed(ConnectionEvent event) {
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