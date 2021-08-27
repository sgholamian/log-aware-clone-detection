//,temp,ConsumerCommand.java,47,91,temp,ProducerCommand.java,49,94
//,3
public class xxx {
    @Override
    protected void runTask(List<String> tokens) throws Exception {
        LOG.info("Connecting to URL: " + brokerUrl + " as user: " + user);
        LOG.info("Consuming " + destination);
        LOG.info("Sleeping between receives " + sleep + " ms");
        LOG.info("Running " + parallelThreads + " parallel threads");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection conn = null;
        try {
            conn = factory.createConnection(user, password);
            if (durable && clientId != null && clientId.length() > 0 && !"null".equals(clientId)) {
                conn.setClientID(clientId);
            }
            conn.start();


            CountDownLatch active = new CountDownLatch(parallelThreads);

            for (int i = 1; i <= parallelThreads; i++) {
                Session sess;
                if (transacted) {
                   sess = conn.createSession(true, Session.SESSION_TRANSACTED);
                } else {
                   sess = conn.createSession(false, ackMode);
                }
                ConsumerThread consumer = new ConsumerThread(sess, ActiveMQDestination.createDestination(destination, ActiveMQDestination.QUEUE_TYPE));
                consumer.setName("consumer-" + i);
                consumer.setDurable(durable);
                consumer.setBreakOnNull(false);
                consumer.setMessageCount(messageCount);
                consumer.setSleep(sleep);
                consumer.setBatchSize(batchSize);
                consumer.setFinished(active);
                consumer.setBytesAsText(bytesAsText);
                consumer.start();
            }

            active.await();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

};