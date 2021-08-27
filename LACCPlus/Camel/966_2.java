//,temp,ThriftConsumerAsyncTest.java,142,169,temp,ThriftConsumerAsyncTest.java,83,111
//,3
public class xxx {
    @Test
    public void testCalculateMethodInvocation() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Work work = new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY);

        thriftClient.calculate(1, work, new AsyncMethodCallback<Integer>() {

            @Override
            public void onComplete(Integer response) {
                calculateResult = response;
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
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "calculate");
        mockEndpoint.assertIsSatisfied();

        assertEquals(THRIFT_TEST_NUM1 * THRIFT_TEST_NUM2, calculateResult);
    }

};