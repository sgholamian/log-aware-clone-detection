//,temp,SubscriptionGatewayIT.java,63,76,temp,FhirMetaIT.java,113,125
//,3
public class xxx {
    @Disabled
    @Test
    public void testDelete() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBraintree.customerId", null);
        // parameter type is String
        headers.put("CamelBraintree.id", null);

        final com.braintreegateway.Result result = requestBodyAndHeaders("direct://DELETE", null, headers);

        assertNotNull(result, "delete result");
        LOG.debug("delete: " + result);
    }

};