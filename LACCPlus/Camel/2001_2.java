//,temp,ThriftProducerAsyncTest.java,79,110,temp,ThriftProducerAsyncTest.java,47,77
//,3
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testCalculateMethodInvocation() throws Exception {
        LOG.info("Thrift calculate method async test start");

        List requestBody = new ArrayList();
        final CountDownLatch latch = new CountDownLatch(1);

        requestBody.add(1);
        requestBody.add(new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY));

        template.asyncCallbackSendBody("direct:thrift-calculate", requestBody, new SynchronizationAdapter() {

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

        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Integer);
        assertEquals(THRIFT_TEST_NUM1 * THRIFT_TEST_NUM2, responseBody);
    }

};