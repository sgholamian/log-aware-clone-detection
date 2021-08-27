//,temp,AMQ4485LowLimitTest.java,294,328,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,183,217
//,2
public class xxx {
    private void startAllGWFanoutConsumers(int nBrokers) throws Exception {

        StringBuffer compositeDest = new StringBuffer();
        for (int k = 0; k < nBrokers; k++) {
            compositeDest.append("GW." + k);
            if (k + 1 != nBrokers) {
                compositeDest.append(',');
            }
        }
        ActiveMQQueue compositeQ = new ActiveMQQueue(compositeDest.toString());

        for (int id = 0; id < nBrokers; id++) {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://localhost:" + (portBase + id) + ")");
            connectionFactory.setWatchTopicAdvisories(false);

            QueueConnection queueConnection = connectionFactory.createQueueConnection();
            queueConnection.start();

            final QueueSession queueSession = queueConnection.createQueueSession(true, Session.SESSION_TRANSACTED);

            final MessageProducer producer = queueSession.createProducer(compositeQ);
            queueSession.createReceiver(new ActiveMQQueue("IN")).setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        producer.send(message);
                        queueSession.commit();
                    } catch (Exception e) {
                        LOG.error("Failed to fanout to GW: " + message, e);
                    }

                }
            });
        }
    }

};