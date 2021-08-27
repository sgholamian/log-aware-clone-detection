//,temp,ConsumerCommand.java,47,91,temp,ProducerCommand.java,49,94
//,3
public class xxx {
    @Override
    protected void runTask(List<String> tokens) throws Exception {
        LOG.info("Connecting to URL: " + brokerUrl + " as user: " + user);
        LOG.info("Producing messages to " + destination);
        LOG.info("Using " + (persistent ? "persistent" : "non-persistent") + " messages");
        LOG.info("Sleeping between sends " + sleep + " ms");
        LOG.info("Running " + parallelThreads + " parallel threads");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection conn = null;
        try {
            conn = factory.createConnection(user, password);
            conn.start();

            CountDownLatch active = new CountDownLatch(parallelThreads);

            for (int i = 1; i <= parallelThreads; i++) {
                Session sess;
                if (transactionBatchSize != 0) {
                    sess = conn.createSession(true, Session.SESSION_TRANSACTED);
                } else {
                    sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                }
                ProducerThread producer = new ProducerThread(sess, ActiveMQDestination.createDestination(destination, ActiveMQDestination.QUEUE_TYPE));
                producer.setName("producer-" + i);
                producer.setMessageCount(messageCount);
                producer.setSleep(sleep);
                producer.setMsgTTL(msgTTL);
                producer.setPersistent(persistent);
                producer.setTransactionBatchSize(transactionBatchSize);
                producer.setMessage(message);
                producer.setPayloadUrl(payloadUrl);
                producer.setMessageSize(messageSize);
                producer.setMsgGroupID(msgGroupID);
                producer.setTextMessageSize(textMessageSize);
                producer.setFinished(active);
                producer.start();
            }

            active.await();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

};