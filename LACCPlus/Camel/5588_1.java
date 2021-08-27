//,temp,PulsarConsumerReadCompactedIT.java,74,81,temp,GroupedExchangeRoundtripIT.java,61,69
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