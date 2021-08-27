//,temp,InOutQueueProducerSyncLoadTest.java,79,110,temp,InOutQueueProducerAsyncLoadTest.java,81,112
//,3
public class xxx {
    @Test
    public void testInOutQueueProducer() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5000; i++) {
            final int tempI = i;
            Runnable worker = new Runnable() {

                @Override
                public void run() {
                    try {
                        final String requestText = "Message " + tempI;
                        final String responseText = "Response Message " + tempI;
                        String response = template.requestBody("direct:start", requestText, String.class);
                        assertNotNull(response);
                        assertEquals(responseText, response);
                    } catch (Exception e) {
                        log.error("TODO Auto-generated catch block", e);
                    }
                }
            };
            executor.execute(worker);
        }
        while (context.getInflightRepository().size() > 0) {
            Thread.sleep(100);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }

};