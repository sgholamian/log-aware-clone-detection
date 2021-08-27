//,temp,GenericFilePollingConsumer.java,90,101,temp,GenericFilePollingConsumer.java,77,88
//,3
public class xxx {
    @Override
    public Exchange receive() {
        if (LOG.isTraceEnabled()) {
            LOG.trace("receive polling file: {}", getConsumer().getEndpoint());
        }
        int polled = doReceive(Long.MAX_VALUE);
        if (polled > 0) {
            return super.receive();
        } else {
            return null;
        }
    }

};