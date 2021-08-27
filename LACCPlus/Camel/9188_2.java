//,temp,NatsProducer.java,141,158,temp,NatsConsumer.java,67,101
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        NatsConfiguration configuration = getEndpoint().getConfiguration();

        if (configuration.isFlushConnection() && ObjectHelper.isNotEmpty(connection)) {
            LOG.debug("Flushing Messages before stopping");
            connection.flush(Duration.ofMillis(configuration.getFlushTimeout()));
        }

        if (ObjectHelper.isNotEmpty(dispatcher)) {
            try {
                dispatcher.unsubscribe(configuration.getTopic());
            } catch (Exception e) {
                getExceptionHandler().handleException("Error during unsubscribing", e);
            }
        }

        LOG.debug("Stopping Nats Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                executor.shutdownNow();
            }
        }
        executor = null;

        if (ObjectHelper.isEmpty(configuration.getConnection()) && ObjectHelper.isNotEmpty(connection)) {
            LOG.debug("Closing Nats Connection");
            if (!connection.getStatus().equals(Status.CLOSED)) {
                connection.close();
            }
        }
        super.doStop();
    }

};