//,temp,ThriftProducerAsyncTest.java,169,194,temp,ThriftProducerAsyncTest.java,142,167
//,2
public class xxx {
    @Test
    public void testOneWayMethodInvocation() throws Exception {
        LOG.info("Thrift one-way method async test start");

        final CountDownLatch latch = new CountDownLatch(1);
        final Object requestBody = null;

        responseBody = new Object();
        template.asyncCallbackSendBody("direct:thrift-zip", requestBody, new SynchronizationAdapter() {

            @Override
            public void onComplete(Exchange exchange) {
                responseBody = exchange.getMessage().getBody();
                latch.countDown();
            }

            @Override
            public void onFailure(Exchange exchange) {
                responseBody = exchange.getException();
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);

        assertNull(responseBody);
    }

};