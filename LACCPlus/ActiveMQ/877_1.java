//,temp,RedeliveryPolicyTest.java,840,855,temp,RedeliveryPolicyTest.java,741,753
//,3
public class xxx {
                @Override
                public void onMessage(Message message) {
                    try {
                        ActiveMQTextMessage m = (ActiveMQTextMessage) message;
                        LOG.info("Got: " + ((ActiveMQTextMessage) message).getMessageId() + ", seq:" + ((ActiveMQTextMessage) message).getMessageId().getBrokerSequenceId() + " ,Redelivery: " + m.getRedeliveryCounter());
                        assertEquals("1st", m.getText());
                        assertEquals(receivedCount.get(), m.getRedeliveryCounter());
                        receivedCount.incrementAndGet();

                        // do a forward of the received message, will reset the messageID
                        forwardingProducer.send(message);
                        done.countDown();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }

};