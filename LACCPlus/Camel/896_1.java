//,temp,ThriftProducerSyncTest.java,141,156,temp,ThriftProducerSyncTest.java,60,75
//,3
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testEchoMethodInvocation() throws Exception {
        LOG.info("Thrift echo method (return output as pass input parameter) sync test start");

        List requestBody = new ArrayList();

        requestBody.add(new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY));

        Object responseBody = template.requestBody("direct:thrift-echo", requestBody);

        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Work);
        assertEquals(THRIFT_TEST_NUM1, ((Work) responseBody).num1);
        assertEquals(Operation.MULTIPLY, ((Work) responseBody).op);
    }

};