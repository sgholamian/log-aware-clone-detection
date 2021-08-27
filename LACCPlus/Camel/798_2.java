//,temp,SmppConsumer.java,79,87,temp,SmppProducer.java,158,166
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        LOG.debug("Disconnecting from: {}...", getEndpoint().getConnectionString());

        super.doStop();
        closeSession();

        LOG.info("Disconnected from: {}", getEndpoint().getConnectionString());
    }

};