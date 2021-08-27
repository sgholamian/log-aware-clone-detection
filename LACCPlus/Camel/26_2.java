//,temp,PaymentMethodNonceGatewayIT.java,49,57,temp,TransactionGatewayIT.java,424,432
//,2
public class xxx {
    @Disabled
    @Test
    public void testReleaseFromEscrow() throws Exception {
        // using String message body for single parameter "id"
        final com.braintreegateway.Result result = requestBody("direct://RELEASEFROMESCROW", null);

        assertNotNull(result, "releaseFromEscrow result");
        LOG.debug("releaseFromEscrow: " + result);
    }

};