//,temp,ThriftProducerSyncTest.java,96,103,temp,ThriftProducerSecurityTest.java,160,167
//,2
public class xxx {
    @Test
    public void testVoidMethodInvocation() throws Exception {
        LOG.info("Thrift method with empty parameters and void output sync test start");

        Object requestBody = null;
        Object responseBody = template.requestBody("direct:thrift-secured-ping", requestBody);
        assertNull(responseBody);
    }

};