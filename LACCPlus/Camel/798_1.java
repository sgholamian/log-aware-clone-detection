//,temp,SmppConsumer.java,79,87,temp,SmppProducer.java,158,166
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        LOG.debug("Connecting to: {}...", getEndpoint().getConnectionString());

        super.doStart();
        session = createSession();

        LOG.info("Connected to: {}", getEndpoint().getConnectionString());
    }

};