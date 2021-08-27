//,temp,PaymentMethodNonceGatewayIT.java,38,46,temp,TransactionGatewayIT.java,462,470
//,2
public class xxx {
    @Disabled
    @Test
    public void testCreate() throws Exception {
        // using String message body for single parameter "paymentMethodToken"
        final com.braintreegateway.Result result = requestBody("direct://CREATE", null);

        assertNotNull(result, "create result");
        LOG.debug("create: " + result);
    }

};