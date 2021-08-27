//,temp,TopicProducerFlowControlTest.java,330,343,temp,AMQDeadlockTestW4Brokers.java,242,264
//,3
public class xxx {
        @Override
        public void onMessage(Message msg) {

            try {
                /*
                 * log.info("Listener1 Consumed message " +
                 * msg.getIntProperty("count") + " from " +
                 * msg.getStringProperty("producerName"));
                 */
                int value = count.incrementAndGet();
                if (value % 1000 == 0) {
                    LOG.info("Consumed message: " + value);
                }

                Thread.sleep(waitTime);
                LATCH.countDown();
                /*
                 * } catch (JMSException e) { e.printStackTrace();
                 */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

};