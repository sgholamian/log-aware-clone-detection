//,temp,JmsConsumerClient.java,166,211,temp,JmsConsumerClient.java,76,101
//,3
public class xxx {
    public void receiveAsyncCountBasedMessages(long count) throws JMSException {
        if (getJmsConsumer() == null) {
            createJmsConsumer();
        }

        final AtomicInteger recvCount = new AtomicInteger(0);
        getJmsConsumer().setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                incThroughput();
                sleep();

                recvCount.incrementAndGet();
                synchronized (recvCount) {
                    recvCount.notify();
                }

                try {
                    commitTxIfNecessary();
                } catch (JMSException ex) {
                    LOG.error("Error committing transaction: " + ex.getMessage());
                }
            }
        });

        try {
            getConnection().start();
            LOG.info("Starting to asynchronously receive " + client.getRecvCount() + " messages...");
            try {
                while (recvCount.get() < count) {
                    synchronized (recvCount) {
                        recvCount.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new JMSException("JMS consumer thread wait has been interrupted. Message: " + e.getMessage());
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