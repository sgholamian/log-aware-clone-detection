//,temp,AbstractJDBCLocker.java,83,91,temp,ActiveMQEndpointWorker.java,245,254
//,3
public class xxx {
    public static void safeClose(ConnectionConsumer cc) {
        try {
            if (cc != null) {
                LOG.debug("Closing ConnectionConsumer");
                cc.close();
            }
        } catch (JMSException e) {
            LOG.trace("failed to close cc {}", cc, e);
        }
    }

};