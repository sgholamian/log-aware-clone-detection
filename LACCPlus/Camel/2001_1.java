//,temp,ThriftProducerAsyncTest.java,79,110,temp,ThriftProducerAsyncTest.java,47,77
//,3
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testAddMethodInvocation() throws Exception {
        LOG.info("Thrift add method (primitive parameters only) async test start");

        final CountDownLatch latch = new CountDownLatch(1);
        List requestBody = new ArrayList();
        responseBody = null;

        requestBody.add(THRIFT_TEST_NUM1);
        requestBody.add(THRIFT_TEST_NUM2);

        template.asyncCallbackSendBody("direct:thrift-add", requestBody, new SynchronizationAdapter() {

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
        assertEquals(THRIFT_TEST_NUM1 + THRIFT_TEST_NUM2, responseBody);
    }

};