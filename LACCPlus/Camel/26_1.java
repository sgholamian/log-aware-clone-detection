//,temp,PaymentMethodNonceGatewayIT.java,49,57,temp,TransactionGatewayIT.java,424,432
//,2
public class xxx {
    @Disabled
    @Test
    public void testFind() throws Exception {
        // using String message body for single parameter "paymentMethodNonce"
        final com.braintreegateway.PaymentMethodNonce result = requestBody("direct://FIND", null);

        assertNotNull(result, "find result");
        LOG.debug("find: " + result);
    }

};