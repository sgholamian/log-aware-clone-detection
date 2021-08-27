//,temp,NatsProducer.java,141,158,temp,NatsConsumer.java,67,101
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (this.scheduler != null) {
            this.executorServiceManager.shutdownNow(this.scheduler);
        }
        LOG.debug("Stopping Nats Producer");
        if (ObjectHelper.isEmpty(getEndpoint().getConfiguration().getConnection())) {
            LOG.debug("Closing Nats Connection");
            if (connection != null && !connection.getStatus().equals(Status.CLOSED)) {
                if (getEndpoint().getConfiguration().isFlushConnection()) {
                    LOG.debug("Flushing Nats Connection");
                    connection.flush(Duration.ofMillis(getEndpoint().getConfiguration().getFlushTimeout()));
                }
                connection.close();
            }
        }
        super.doStop();
    }

};