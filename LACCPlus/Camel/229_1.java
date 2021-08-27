//,temp,ThriftProducerZlibCompressionTest.java,92,107,temp,ThriftProducerSyncTest.java,43,58
//,2
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testCalculateMethodInvocation() throws Exception {
        LOG.info("Thrift calculate method sync test start");

        List requestBody = new ArrayList();

        requestBody.add(1);
        requestBody.add(new Work(THRIFT_TEST_NUM1, THRIFT_TEST_NUM2, Operation.MULTIPLY));

        Object responseBody = template.requestBody("direct:thrift-zlib-calculate", requestBody);

        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Integer);
        assertEquals(THRIFT_TEST_NUM1 * THRIFT_TEST_NUM2, responseBody);
    }

};