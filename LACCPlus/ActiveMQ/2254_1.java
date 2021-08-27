//,temp,JmsConsumerClient.java,130,164,temp,JmsConsumerClient.java,103,128
//,3
public class xxx {
    public void receiveAsyncTimeBasedMessages(long duration) throws JMSException {
        if (getJmsConsumer() == null) {
            createJmsConsumer();
        }

        getJmsConsumer().setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                incThroughput();
                sleep();
                try {
                    commitTxIfNecessary();
                } catch (JMSException ex) {
                    LOG.error("Error committing transaction: " + ex.getMessage());
                }
            }
        });

        try {
            getConnection().start();
            LOG.info("Starting to asynchronously receive messages for " + duration + " ms...");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                throw new JMSException("JMS consumer thread sleep has been interrupted. Message: " + e.getMessage());
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