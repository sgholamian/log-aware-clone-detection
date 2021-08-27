//,temp,GenericFilePollingConsumer.java,90,101,temp,GenericFilePollingConsumer.java,77,88
//,3
public class xxx {
    @Override
    public Exchange receiveNoWait() {
        if (LOG.isTraceEnabled()) {
            LOG.trace("receiveNoWait polling file: {}", getConsumer().getEndpoint());
        }
        int polled = doReceive(0);
        if (polled > 0) {
            return super.receive(0);
        } else {
            return null;
        }
    }

};