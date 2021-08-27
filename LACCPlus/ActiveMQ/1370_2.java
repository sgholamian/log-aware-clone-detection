//,temp,DestinationInterceptorDurableSubTest.java,251,259,temp,WebClient.java,233,238
//,3
public class xxx {
    public void send(Destination destination, Message message) throws JMSException {
        getProducer().send(destination, message);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sent! to destination: " + destination + " message: " + message);
        }
    }

};