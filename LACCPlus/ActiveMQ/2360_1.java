//,temp,ConcurrentProducerQueueConsumerTest.java,373,402,temp,ConcurrentProducerDurableConsumerTest.java,372,400
//,3
public class xxx {
        @Override
        public void onMessage(Message message) {
            final long current = System.currentTimeMillis();
            final long duration = current - mark;
            receiptAccumulator += duration;
            int priority = 0;

            try {
                priority = message.getJMSPriority();
            } catch (JMSException ignored) {}

            if (!messageLists.containsKey(priority)) {
                messageLists.put(priority, new MessageIdList());
            }
            messageLists.get(priority).onMessage(message);

            if (count.incrementAndGet() == 1) {
                firstReceipt = duration;
                firstReceiptLatch.countDown();
                LOG.info("First receipt in " + firstReceipt + "ms");
            } else if (count.get() % batchSize == 0) {
                LOG.info("Consumed " + count.get() + " in " + batchReceiptAccumulator + "ms" + ", priority:" + priority);
                batchReceiptAccumulator=0;
            }

            maxReceiptTime = Math.max(maxReceiptTime, duration);
            receiptAccumulator += duration;
            batchReceiptAccumulator += duration;
            mark = current;
        }

};