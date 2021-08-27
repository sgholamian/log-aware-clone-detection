//,temp,BoxFoldersManagerIT.java,180,197,temp,SubscriptionGatewayIT.java,101,114
//,3
public class xxx {
    @Disabled
    @Test
    public void testRetryChargeWithAmount() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBraintree.subscriptionId", null);
        // parameter type is java.math.BigDecimal
        headers.put("CamelBraintree.amount", null);

        final com.braintreegateway.Result result = requestBodyAndHeaders("direct://RETRYCHARGE_1", null, headers);

        assertNotNull(result, "retryCharge result");
        LOG.debug("retryCharge: " + result);
    }

};