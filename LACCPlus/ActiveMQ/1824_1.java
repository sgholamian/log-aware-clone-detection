//,temp,KahaDBFastEnqueueTest.java,105,147,temp,KahaDBFastEnqueueTest.java,65,103
//,3
public class xxx {
    @Test
    public void testPublishNoConsumerNoCheckpoint() throws Exception {

        toSend = 100;
        startBroker(true, 0);

        final AtomicLong sharedCount = new AtomicLong(toSend);
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i< parallelProducer; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        publishMessages(sharedCount, 0);
                    } catch (Exception e) {
                        exceptions.add(e);
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.MINUTES);
        assertTrue("Producers done in time", executorService.isTerminated());
        assertTrue("No exceptions: " + exceptions, exceptions.isEmpty());
        long totalSent  = toSend * payloadString.length();

        broker.getAdminView().gc();


        double duration =  System.currentTimeMillis() - start;
        stopBroker();
        LOG.info("Duration:                " + duration + "ms");
        LOG.info("Rate:                       " + (toSend * 1000/duration) + "m/s");
        LOG.info("Total send:             " + totalSent);
        LOG.info("Total journal write: " + kahaDBPersistenceAdapter.getStore().getJournal().length());
        LOG.info("Total index size " + kahaDBPersistenceAdapter.getStore().getPageFile().getDiskSize());
        LOG.info("Total store size: " + kahaDBPersistenceAdapter.size());
        LOG.info("Journal writes %:    " + kahaDBPersistenceAdapter.getStore().getJournal().length() / (double)totalSent * 100 + "%");

        restartBroker(0, 0);
        consumeMessages(toSend);
    }

};