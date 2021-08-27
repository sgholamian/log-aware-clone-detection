//,temp,AMQDeadlockTest3.java,382,424,temp,AMQDeadlockTest3.java,327,368
//,2
public class xxx {
        @Override
        public void run() {

            try {

                final JmsTemplate jmsTemplate = new JmsTemplate(pcf);
                jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                jmsTemplate.setExplicitQosEnabled(true);
                jmsTemplate.setMessageIdEnabled(false);
                jmsTemplate.setMessageTimestampEnabled(false);
                jmsTemplate.afterPropertiesSet();

                final byte[] bytes = new byte[2048];
                final Random r = new Random();
                r.nextBytes(bytes);

                Thread.sleep(2000);

                final AtomicInteger count = new AtomicInteger();
                for (int i = 0; i < NUM_MESSAGE_TO_SEND; i++) {
                    jmsTemplate.send(queueName, new MessageCreator() {

                        @Override
                        public Message createMessage(Session session) throws JMSException {

                            final BytesMessage message = session.createBytesMessage();

                            message.writeBytes(bytes);
                            message.setIntProperty("count", count.incrementAndGet());
                            message.setStringProperty("producer", "pooled");
                            return message;
                        }
                    });

                    LOG.info("PooledProducer sent message: " + count.get());
                    // Thread.sleep(1000);
                }

            } catch (final Throwable e) {
                LOG.error("Producer 1 is exiting", e);
            }
        }

};