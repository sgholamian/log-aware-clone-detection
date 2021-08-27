//,temp,AMQ4485LowLimitTest.java,330,385,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,219,273
//,2
public class xxx {
    private List<ConsumerState> startAllGWConsumers(int nBrokers) throws Exception {
        List<ConsumerState> consumerStates = new LinkedList<ConsumerState>();
        for (int id = 0; id < nBrokers; id++) {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://localhost:" + (portBase + id) + ")");
            connectionFactory.setWatchTopicAdvisories(false);

            QueueConnection queueConnection = connectionFactory.createQueueConnection();
            queueConnection.start();

            final QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            ActiveMQQueue destination = new ActiveMQQueue("GW." + id);
            QueueReceiver queueReceiver = queueSession.createReceiver(destination);

            final ConsumerState consumerState = new ConsumerState();
            consumerState.brokerName = ((ActiveMQConnection) queueConnection).getBrokerName();
            consumerState.receiver = queueReceiver;
            consumerState.destination = destination;
            for (int j = 0; j < numMessages * (consumerState.destination.isComposite() ? consumerState.destination.getCompositeDestinations().length : 1); j++) {
                consumerState.expected.add(j);
            }

            if (!accumulators.containsKey(destination)) {
                accumulators.put(destination, new AtomicInteger(0));
            }
            consumerState.accumulator = accumulators.get(destination);

            queueReceiver.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (consumerSleepTime > 0) {
                            TimeUnit.MILLISECONDS.sleep(consumerSleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        consumerState.accumulator.incrementAndGet();
                        try {
                            consumerState.expected.remove(((ActiveMQMessage) message).getProperty("NUM"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //queueSession.commit();
                    } catch (Exception e) {
                        LOG.error("Failed to commit slow receipt of " + message, e);
                    }
                }
            });

            consumerStates.add(consumerState);

        }
        return consumerStates;
    }

};