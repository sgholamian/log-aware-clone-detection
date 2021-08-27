//,temp,sample_3990.java,2,14,temp,sample_24.java,2,14
//,2
public class xxx {
public void addConsumer(NettyConsumer consumer) {
if (compatibleCheck) {
if (bootstrapConfiguration != consumer.getConfiguration() && !bootstrapConfiguration.compatible(consumer.getConfiguration())) {
throw new IllegalArgumentException("Bootstrap configuration must be identical when adding additional consumer: " + consumer.getEndpoint() + " on same port: " + port + ".\n  Existing " + bootstrapConfiguration.toStringBootstrapConfiguration() + "\n       New " + consumer.getConfiguration().toStringBootstrapConfiguration());
}
}
if (LOG.isDebugEnabled()) {
NettyHttpConsumer httpConsumer = (NettyHttpConsumer) consumer;


log.info("bootstrapfactory on port is adding consumer with context path");
}
}

};