//,temp,JmsConsumerClient.java,130,164,temp,JmsConsumerClient.java,103,128
//,3
public class xxx {
    public void receiveSyncCountBasedMessages(long count) throws JMSException {
        if (getJmsConsumer() == null) {
            createJmsConsumer();
        }

        try {
            getConnection().start();
            LOG.info("Starting to synchronously receive " + count + " messages...");

            int recvCount = 0;
            while (recvCount < count) {
                getJmsConsumer().receive();
                incThroughput();
                recvCount++;
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