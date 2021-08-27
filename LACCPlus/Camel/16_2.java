//,temp,TransactionGatewayIT.java,413,421,temp,TransactionGatewayIT.java,391,399
//,2
public class xxx {
    @Disabled
    @Test
    public void testCancelRelease() throws Exception {
        // using String message body for single parameter "id"
        final com.braintreegateway.Result result = requestBody("direct://CANCELRELEASE", null);

        assertNotNull(result, "cancelRelease result");
        LOG.debug("cancelRelease: " + result);
    }

};