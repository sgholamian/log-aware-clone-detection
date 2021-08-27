//,temp,ThriftConsumerZlibCompressionTest.java,78,92,temp,ThriftConsumerSecurityTest.java,106,120
//,2
public class xxx {
    @Test
    public void testCalculateMethodInvocation() throws Exception {
        LOG.info("Test Calculate method invocation");

        Work work = new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY);

        int calculateResult = thriftClient.calculate(1, work);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:thrift-secure-service");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "calculate");
        mockEndpoint.assertIsSatisfied();

        assertEquals(THRIFT_TEST_NUM1 * THRIFT_TEST_NUM2, calculateResult);
    }

};