//,temp,ThriftConsumerAsyncTest.java,206,237,temp,ThriftConsumerAsyncTest.java,113,140
//,3
public class xxx {
    @Test
    public void testVoidMethodInvocation() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        thriftClient.ping(new AsyncMethodCallback<Void>() {

            @Override
            public void onComplete(Void response) {
                pingResult = 0;
                latch.countDown();
            }

            @Override
            public void onError(Exception exception) {
                LOG.info("Exception", exception);
                latch.countDown();
            }

        });
        latch.await(5, TimeUnit.SECONDS);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:thrift-service");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "ping");
        mockEndpoint.assertIsSatisfied();

        assertEquals(0, pingResult);
    }

};