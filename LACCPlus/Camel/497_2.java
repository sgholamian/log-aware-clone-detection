//,temp,PulsarConsumerAcknowledgementTest.java,84,93,temp,PulsarConsumerDeadLetterPolicyIT.java,94,101
//,3
public class xxx {
    @AfterEach
    public void tearDownProducer() {
        try {
            producer.close();
        } catch (PulsarClientException e) {
            LOGGER.warn("Failed to close client: {}", e.getMessage(), e);
        }
    }

};