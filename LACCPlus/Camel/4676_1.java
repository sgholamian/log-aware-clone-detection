//,temp,ThriftProducerAsyncTest.java,196,237,temp,ThriftProducerSyncTest.java,114,139
//,3
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testAllTypesMethodInvocation() throws Exception {
        LOG.info("Thrift method with all possile types async test start");

        final CountDownLatch latch = new CountDownLatch(1);
        List requestBody = new ArrayList();

        requestBody.add(true);
        requestBody.add((byte) THRIFT_TEST_NUM1);
        requestBody.add((short) THRIFT_TEST_NUM1);
        requestBody.add(THRIFT_TEST_NUM1);
        requestBody.add((long) THRIFT_TEST_NUM1);
        requestBody.add((double) THRIFT_TEST_NUM1);
        requestBody.add("empty");
        requestBody.add(ByteBuffer.allocate(10));
        requestBody.add(new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY));
        requestBody.add(new ArrayList<Integer>());
        requestBody.add(new HashSet<String>());
        requestBody.add(new HashMap<String, Long>());

        responseBody = new Object();
        template.asyncCallbackSendBody("direct:thrift-alltypes", requestBody, new SynchronizationAdapter() {

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
        assertEquals(1, responseBody);
    }

};