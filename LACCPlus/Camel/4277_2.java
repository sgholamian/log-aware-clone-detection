//,temp,NatsProducer.java,122,139,temp,NatsConsumer.java,54,65
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();
        LOG.debug("Starting Nats Consumer");
        executor = getEndpoint().createExecutor();

        LOG.debug("Getting Nats Connection");
        connection = getEndpoint().getConfiguration().getConnection() != null
                ? getEndpoint().getConfiguration().getConnection() : getEndpoint().getConnection();

        executor.submit(new NatsConsumingTask(connection, getEndpoint().getConfiguration()));
    }

};