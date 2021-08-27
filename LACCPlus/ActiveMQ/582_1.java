//,temp,PublishOnTopicConsumerMessageUsingActivemqXMLTest.java,69,75,temp,PublishOnQueueConsumedMessageUsingActivemqXMLTest.java,68,74
//,2
public class xxx {
    protected void tearDown() throws Exception {
        LOG.info("Closing Broker");
        if (broker != null) {
            broker.stop();
        }
        LOG.info("Broker closed...");
    }

};