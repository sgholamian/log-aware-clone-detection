//,temp,ThriftConsumerAsyncTest.java,142,169,temp,ThriftConsumerAsyncTest.java,83,111
//,3
public class xxx {
    @Test
    public void testOneWayMethodInvocation() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        thriftClient.zip(new AsyncMethodCallback<Void>() {

            @Override
            public void onComplete(Void response) {
                zipResult = 0;
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
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "zip");
        mockEndpoint.assertIsSatisfied();

        assertEquals(0, zipResult);
    }

};