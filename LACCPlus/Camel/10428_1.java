//,temp,DataFormatConcurrentTest.java,191,215,temp,DataFormatConcurrentTest.java,165,189
//,2
public class xxx {
    public void marshal(final CountDownLatch latch) throws Exception {
        // warm up
        Foo[] warmUpPayloads = createFoo(warmupCount);
        for (Foo payload : warmUpPayloads) {
            template.sendBody(payload);
        }

        final Foo[] payloads = createFoo(testCycleCount);
        ExecutorService pool = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < payloads.length; i++) {
            final int finalI = i;
            pool.execute(new Runnable() {
                public void run() {
                    template.sendBody(payloads[finalI]);
                }
            });
        }

        latch.await();
        long end = System.currentTimeMillis();

        LOG.info("Sending {} messages to {} took {} ms",
                new Object[] { payloads.length, template.getDefaultEndpoint().getEndpointUri(), end - start });
    }

};