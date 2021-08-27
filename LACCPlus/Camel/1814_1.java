//,temp,ThriftConsumerAsyncTest.java,206,237,temp,ThriftConsumerAsyncTest.java,113,140
//,3
public class xxx {
    @Test
    public void testEchoMethodInvocation() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Work work = new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY);

        thriftClient.echo(work, new AsyncMethodCallback<Work>() {

            @Override
            public void onComplete(Work response) {
                echoResult = response;
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
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "echo");
        mockEndpoint.assertIsSatisfied();

        assertNotNull(echoResult);
        assertTrue(echoResult instanceof Work);
        assertEquals(THRIFT_TEST_NUM1, echoResult.num1);
        assertEquals(Operation.MULTIPLY, echoResult.op);
    }

};