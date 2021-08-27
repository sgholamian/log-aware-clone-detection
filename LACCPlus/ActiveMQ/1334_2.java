//,temp,ConcurrentProducerQueueConsumerTest.java,239,281,temp,ConcurrentProducerDurableConsumerTest.java,245,281
//,2
public class xxx {
    private double[] produceMessages(Destination destination, final int toSend, final int numIterations, Session session, MessageProducer producer,
        Object addConsumerSignal) throws Exception {
        long start;
        long count = 0;
        double batchMax = 0, max = 0, sum = 0;
        for (int i = 0; i < numIterations; i++) {
            start = System.currentTimeMillis();
            for (int j = 0; j < toSend; j++) {
                long singleSendstart = System.currentTimeMillis();
                TextMessage msg = createTextMessage(session, "" + j);
                // rotate
                int priority = ((int) count % 10);
                producer.send(msg, DeliveryMode.PERSISTENT, priority, 0);
                max = Math.max(max, (System.currentTimeMillis() - singleSendstart));
                if (++count % 500 == 0) {
                    if (addConsumerSignal != null) {
                        synchronized (addConsumerSignal) {
                            addConsumerSignal.notifyAll();
                            LOG.info("Signalled add consumer");
                        }
                    }
                }
                ;
                if (count % 5000 == 0) {
                    LOG.info("Sent " + count + ", singleSendMax:" + max);
                }

            }
            long duration = System.currentTimeMillis() - start;
            batchMax = Math.max(batchMax, duration);
            sum += duration;
            LOG.info("Iteration " + i + ", sent " + toSend + ", time: " + duration + ", batchMax:" + batchMax + ", singleSendMax:" + max);
        }

        LOG.info("Sent: " + toSend * numIterations + ", batchMax: " + batchMax + " singleSendMax: " + max);
        return new double[] { batchMax, sum / numIterations };
    }

};