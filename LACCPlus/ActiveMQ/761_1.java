//,temp,RemoteJMXBrokerFacade.java,157,166,temp,ActiveMQEndpointWorker.java,231,240
//,3
public class xxx {
    protected synchronized void closeConnection() {
        if (connector != null) {
            try {
                LOG.debug("Closing a connection to a broker (" + connector.getConnectionId() + ")");
                connector.close();
            } catch (IOException e) {
                // Ignore the exception, since it most likly won't matter anymore
            }
        }
    }

};