//,temp,ThriftConsumerZlibCompressionTest.java,94,109,temp,ThriftConsumerSecurityTest.java,122,137
//,2
public class xxx {
    @Test
    public void testEchoMethodInvocation() throws Exception {
        LOG.info("Test Echo method invocation");

        Work echoResult = thriftClient.echo(new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY));

        MockEndpoint mockEndpoint = getMockEndpoint("mock:thrift-secure-service");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedHeaderValuesReceivedInAnyOrder(ThriftConstants.THRIFT_METHOD_NAME_HEADER, "echo");
        mockEndpoint.assertIsSatisfied();

        assertNotNull(echoResult);
        assertTrue(echoResult instanceof Work);
        assertEquals(THRIFT_TEST_NUM1, echoResult.num1);
        assertEquals(Operation.MULTIPLY, echoResult.op);
    }

};