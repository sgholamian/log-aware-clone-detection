//,temp,PublishOnTopicConsumerMessageUsingActivemqXMLTest.java,55,63,temp,PublishOnQueueConsumedMessageUsingActivemqXMLTest.java,54,62
//,2
public class xxx {
    protected void setUp() throws Exception {
        File journalFile = new File(JOURNAL_ROOT);
        recursiveDelete(journalFile);
        // Create broker from resource
        LOG.info("Creating broker... ");
        broker = createBroker("org/apache/activemq/usecases/activemq.xml");
        LOG.info("Success");
        super.setUp();
    }

};