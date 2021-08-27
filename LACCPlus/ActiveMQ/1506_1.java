//,temp,ConcurrentProducerDurableConsumerTest.java,411,426,temp,AMQ2584ConcurrentDlqTest.java,174,187
//,3
public class xxx {
        public long waitForReceivedLimit(long limit) throws Exception {
            final long expiry = System.currentTimeMillis() + 30 * 60 * 1000;
            while (count.get() < limit) {
                if (System.currentTimeMillis() > expiry) {
                    throw new RuntimeException("Expired waiting for X messages, " + limit);
                }
                TimeUnit.SECONDS.sleep(2);
                String missing = findFirstMissingMessage();
                if (missing != null) {
                    LOG.info("first missing = " + missing);
                    throw new RuntimeException("We have a missing message. " + missing);
                }

            }
            return receiptAccumulator / (limit / batchSize);
        }

};