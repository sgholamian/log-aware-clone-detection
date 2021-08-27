//,temp,RemoteJMXBrokerFacade.java,157,166,temp,ActiveMQEndpointWorker.java,231,240
//,3
public class xxx {
    public static void safeClose(Connection c) {
        try {
            if (c != null) {
                LOG.debug("Closing connection to broker");
                c.close();
            }
        } catch (JMSException e) {
            LOG.trace("failed to close c {}", c, e);
        }
    }

};