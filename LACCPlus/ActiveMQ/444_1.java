//,temp,AMQ5266SingleDestTest.java,143,230,temp,AMQ5266StarvedConsumerTest.java,159,235
//,3
public class xxx {
    @Test
    public void test() throws Exception {

        String activemqQueues = "activemq";
        for (int i=1;i<numDests;i++) {
            activemqQueues +=",activemq"+i;
        }

        int consumerWaitForConsumption = 5 * 60 * 1000;

        ExportQueuePublisher publisher = null;
        ExportQueueConsumer consumer = null;

        LOG.info("Publisher will publish " + (publisherMessagesPerThread * publisherThreadCount) + " messages to each queue specified.");
        LOG.info("\nBuilding Publisher...");

        publisher = new ExportQueuePublisher(activemqURL, activemqQueues, publisherMessagesPerThread, publisherThreadCount);

        LOG.info("Building Consumer...");

        consumer = new ExportQueueConsumer(activemqURL, activemqQueues, consumerThreadsPerQueue, consumerBatchSize, publisherMessagesPerThread * publisherThreadCount);

        long totalStart = System.currentTimeMillis();

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
        LOG.info("Publisher duration: {}", TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - totalStart));


        long endWait = System.currentTimeMillis() + consumerWaitForConsumption;
        while (!consumer.completed() && System.currentTimeMillis() < endWait) {
            try {
                int secs = (int) (endWait - System.currentTimeMillis()) / 1000;
                LOG.info("Waiting For Consumer Completion. Time left: " + secs + " secs");
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        LOG.info("\nConsumer Complete: " + consumer.completed() +", Shutting Down.");

        LOG.info("Total duration: {}", TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - totalStart));

        consumer.shutdown();

        TimeUnit.SECONDS.sleep(2);

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

        // verify empty dlq
        assertEquals("No pending messages", 0l, ((RegionBroker) brokerService.getRegionBroker()).getDestinationStatistics().getMessages().getCount());
    }

};