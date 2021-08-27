//,temp,ActiveMQEmbeddedService.java,80,92,temp,ActiveMQEmbeddedService.java,55,66
//,3
public class xxx {
    public void restart() {
        shutdown();

        LOG.info("Trying to start the restart ActiveMQ");
        try {
            brokerService.start(true);
            brokerService.waitUntilStarted();
            LOG.info("Embedded ActiveMQ running at {}", serviceAddress());
        } catch (Exception e) {
            LOG.warn("Unable to start embedded ActiveMQ broker: {}", e.getMessage(), e);
            fail(e.getMessage());
        }
    }

};