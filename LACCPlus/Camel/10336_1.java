//,temp,ThriftProducerSyncTest.java,77,94,temp,ThriftProducerSecurityTest.java,141,158
//,2
public class xxx {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testCalculateWithException() throws Exception {
        LOG.info("Thrift calculate method with business exception sync test start");

        List requestBody = new ArrayList();

        requestBody.add(1);
        requestBody.add(new Work(THRIFT_TEST_NUM1, 0, Operation.DIVIDE));

        try {
            template.requestBody("direct:thrift-calculate", requestBody);
            fail("Expect the exception here");
        } catch (Exception ex) {
            assertTrue(ex instanceof CamelExecutionException, "Expect CamelExecutionException");
            assertTrue(ex.getCause() instanceof InvalidOperation, "Get an InvalidOperation exception");
        }
    }

};