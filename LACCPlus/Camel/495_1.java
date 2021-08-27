//,temp,SubscriptionGatewayIT.java,52,60,temp,CreditCardVerificationGatewayIT.java,40,48
//,3
public class xxx {
    @Disabled
    @Test
    public void testCreate() throws Exception {
        // using com.braintreegateway.SubscriptionRequest message body for single parameter "request"
        final com.braintreegateway.Result result = requestBody("direct://CREATE", null);

        assertNotNull(result, "create result");
        LOG.debug("create: " + result);
    }

};