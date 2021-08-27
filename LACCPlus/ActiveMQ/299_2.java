//,temp,JmsConsumerClient.java,166,211,temp,JmsConsumerClient.java,76,101
//,3
public class xxx {
    public void receiveSyncTimeBasedMessages(long duration) throws JMSException {
        if (getJmsConsumer() == null) {
            createJmsConsumer();
        }

        try {
            getConnection().start();

            LOG.info("Starting to synchronously receive messages for " + duration + " ms...");
            long endTime = System.currentTimeMillis() + duration;

            while (System.currentTimeMillis() - endTime < 0) {
                getJmsConsumer().receive();
                incThroughput();
                sleep();
                commitTxIfNecessary();
            }
        } finally {
            if (client.isDurable() && client.isUnsubscribe()) {
                LOG.info("Unsubscribing durable subscriber: " + getClientName());
                getJmsConsumer().close();
                getSession().unsubscribe(getClientName());
            }
            getConnection().close();
        }
    }

};