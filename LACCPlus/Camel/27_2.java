//,temp,PaymentMethodNonceGatewayIT.java,38,46,temp,TransactionGatewayIT.java,462,470
//,2
public class xxx {
    @Disabled
    @Test
    public void testVoidTransaction() throws Exception {
        // using String message body for single parameter "id"
        final com.braintreegateway.Result result = requestBody("direct://VOIDTRANSACTION", null);

        assertNotNull(result, "voidTransaction result");
        LOG.debug("voidTransaction: " + result);
    }

};