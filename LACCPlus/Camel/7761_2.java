//,temp,DirectVmProducerBlockingTest.java,86,111,temp,DirectProducerBlockingTest.java,71,96
//,2
public class xxx {
    @Test
    public void testProducerBlocksResumeTest() throws Exception {
        context.getRouteController().suspendRoute("foo");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    log.info("Resuming consumer");
                    context.getRouteController().resumeRoute("foo");
                } catch (Exception e) {
                    // ignore
                }
            }
        });

        getMockEndpoint("mock:result").expectedMessageCount(1);

        template.sendBody("direct:suspended?block=true&timeout=1000", "hello world");

        assertMockEndpointsSatisfied();

        executor.shutdownNow();
    }

};