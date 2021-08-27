//,temp,ActiveMQEmbeddedService.java,80,92,temp,ActiveMQEmbeddedService.java,55,66
//,3
public class xxx {
    @Override
    public void initialize() {
        LOG.info("Trying to start the embedded ActiveMQ");
        try {
            brokerService.start();
            brokerService.waitUntilStarted();
            LOG.info("Embedded ActiveMQ running at {}", serviceAddress());
        } catch (Exception e) {
            LOG.warn("Unable to start embedded ActiveMQ broker: {}", e.getMessage(), e);
            fail(e.getMessage());
        }
    }

};