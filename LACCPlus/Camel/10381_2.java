//,temp,ThriftProducerAsyncTest.java,169,194,temp,ThriftProducerAsyncTest.java,142,167
//,2
public class xxx {
    @Test
    public void testVoidMethodInvocation() throws Exception {
        LOG.info("Thrift method with empty parameters and void output async test start");

        final CountDownLatch latch = new CountDownLatch(1);
        final Object requestBody = null;

        responseBody = new Object();
        template.asyncCallbackSendBody("direct:thrift-ping", requestBody, new SynchronizationAdapter() {

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