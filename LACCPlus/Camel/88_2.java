//,temp,ThriftProducerZlibCompressionTest.java,109,116,temp,ThriftProducerSyncTest.java,105,112
//,2
public class xxx {
    @Test
    public void testOneWayMethodInvocation() throws Exception {
        LOG.info("Thrift one-way method sync test start");

        Object requestBody = null;
        Object responseBody = template.requestBody("direct:thrift-zip", requestBody);
        assertNull(responseBody);
    }

};