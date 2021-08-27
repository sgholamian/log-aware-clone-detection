//,temp,SimpleConnectionManager.java,57,68,temp,ConnectionManagerAdapter.java,80,90
//,3
public class xxx {
    @Override
    public void connectionClosed(ConnectionEvent event) {
        connections.remove(event.getSource());
        try {
            ((ManagedConnection)event.getSource()).cleanup();
        } catch (ResourceException e) {
            LOG.warn("Error occured during the cleanup of a managed connection: ", e);
        }

        // should go back in a pool, no destroy
    }

};