//,temp,ThriftProducerSyncTest.java,141,156,temp,ThriftProducerSyncTest.java,60,75
//,3
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testAddMethodInvocation() throws Exception {
        LOG.info("Thrift add method (primitive parameters only) sync test start");

        List requestBody = new ArrayList();

        requestBody.add(THRIFT_TEST_NUM1);
        requestBody.add(THRIFT_TEST_NUM2);

        Object responseBody = template.requestBody("direct:thrift-add", requestBody);

        assertNotNull(responseBody);
        assertTrue(responseBody instanceof Integer);
        assertEquals(THRIFT_TEST_NUM1 + THRIFT_TEST_NUM2, responseBody);
    }

};