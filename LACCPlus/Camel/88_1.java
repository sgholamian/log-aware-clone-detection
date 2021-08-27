//,temp,ThriftProducerZlibCompressionTest.java,109,116,temp,ThriftProducerSyncTest.java,105,112
//,2
public class xxx {
    @Test
    public void testVoidMethodInvocation() throws Exception {
        LOG.info("Thrift method with empty parameters and void output sync test start");

        Object requestBody = null;
        Object responseBody = template.requestBody("direct:thrift-zlib-ping", requestBody);
        assertNull(responseBody);
    }

};