//,temp,TopicProducerFlowControlTest.java,330,343,temp,AMQDeadlockTestW4Brokers.java,242,264
//,3
public class xxx {
    @Override
    public void onMessage(Message message) {
        long count = consumed.incrementAndGet();
        if (count % 100 == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        if (count % 10000 == 0) {
            LOG.info("\tConsumed " + count + " messages");
        }

    }

};