//,temp,TempDestLoadTest.java,73,98,temp,TempDestLoadTest.java,44,71
//,2
public class xxx {
    public void testLoadTempAdvisoryTopics() throws Exception {
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            TemporaryTopic tempTopic =  session.createTemporaryTopic();
            MessageConsumer consumer = session.createConsumer(tempTopic);
            MessageProducer producer = session.createProducer(tempTopic);
            consumer.close();
            producer.close();
            tempTopic.delete();
        }

        AdvisoryBroker ab = (AdvisoryBroker) broker.getBroker().getAdaptor(
                AdvisoryBroker.class);
        assertTrue(ab.getAdvisoryDestinations().size() == 0);
        assertTrue(ab.getAdvisoryConsumers().size() == 0);
        assertTrue(ab.getAdvisoryProducers().size() == 0);
        RegionBroker rb = (RegionBroker) broker.getBroker().getAdaptor(
                RegionBroker.class);

        for (Destination dest : rb.getDestinationMap().values()) {
            LOG.debug("Destination: {}", dest);
        }

        // there should be at least 2 destinations - advisories -
        // 1 for the connection + 1 generic ones
        assertTrue("Should be at least 2 destinations", rb.getDestinationMap().size() > 2);
    }

};