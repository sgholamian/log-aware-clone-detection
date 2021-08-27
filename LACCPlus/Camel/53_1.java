//,temp,SubscriptionGatewayIT.java,90,98,temp,TransactionGatewayIT.java,435,443
//,2
public class xxx {
    @Disabled
    @Test
    public void testRetryCharge() throws Exception {
        // using String message body for single parameter "subscriptionId"
        final com.braintreegateway.Result result = requestBody("direct://RETRYCHARGE", null);

        assertNotNull(result, "retryCharge result");
        LOG.debug("retryCharge: " + result);
    }

};