//,temp,AMQ5266SingleDestTest.java,143,230,temp,AMQ5266StarvedConsumerTest.java,159,235
//,3
public class xxx {
    @Test(timeout = 30 * 60 * 1000)
    public void test() throws Exception {

        String activemqQueues = "activemq,activemq2,activemq3,activemq4";//,activemq5,activemq6,activemq7,activemq8,activemq9";

        int consumerWaitForConsumption = 5 * 60 * 1000;

        ExportQueuePublisher publisher = null;
        ExportQueueConsumer consumer = null;

        LOG.info("Publisher will publish " + (publisherMessagesPerThread * publisherThreadCount) + " messages to each queue specified.");
        LOG.info("\nBuilding Publisher...");

        publisher = new ExportQueuePublisher(activemqURL, activemqQueues, publisherMessagesPerThread, publisherThreadCount);

        LOG.info("Building Consumer...");

        consumer = new ExportQueueConsumer(activemqURL, activemqQueues, consumerThreadsPerQueue, consumerBatchSize, publisherMessagesPerThread * publisherThreadCount);


        LOG.info("Starting Publisher...");

        publisher.start();

        LOG.info("Starting Consumer...");

        consumer.start();

        int distinctPublishedCount = 0;


        LOG.info("Waiting For Publisher Completion...");

        publisher.waitForCompletion();

        List publishedIds = publisher.getIDs();
        distinctPublishedCount = new TreeSet(publishedIds).size();

        LOG.info("Publisher Complete. Published: " + publishedIds.size() + ", Distinct IDs Published: " + distinctPublishedCount);


        long endWait = System.currentTimeMillis() + consumerWaitForConsumption;
        while (!consumer.completed() && System.currentTimeMillis() < endWait) {
            try {
                int secs = (int) (endWait - System.currentTimeMillis()) / 1000;
                LOG.info("Waiting For Consumer Completion. Time left: " + secs + " secs");
                Thread.sleep(10000);
            } catch (Exception e) {
            }
        }

        LOG.info("\nConsumer Complete: " + consumer.completed() +", Shutting Down.");

        consumer.shutdown();


        LOG.info("Consumer Stats:");

        for (Map.Entry<String, List<String>> entry : consumer.getIDs().entrySet()) {

            List<String> idList = entry.getValue();

            int distinctConsumed = new TreeSet<String>(idList).size();

            StringBuilder sb = new StringBuilder();
            sb.append("   Queue: " + entry.getKey() +
                    " -> Total Messages Consumed: " + idList.size() +
                    ", Distinct IDs Consumed: " + distinctConsumed);

            int diff = distinctPublishedCount - distinctConsumed;
            sb.append(" ( " + (diff > 0 ? diff : "NO") + " STUCK MESSAGES " + " ) ");
            LOG.info(sb.toString());

            assertEquals("expect to get all messages!", 0, diff);

        }
    }

};