//,temp,SubscriptionGatewayIT.java,90,98,temp,TransactionGatewayIT.java,435,443
//,2
public class xxx {
    @Disabled
    @Test
    public void testSearch() throws Exception {
        // using com.braintreegateway.TransactionSearchRequest message body for single parameter "query"
        final com.braintreegateway.ResourceCollection result = requestBody("direct://SEARCH", null);

        assertNotNull(result, "search result");
        LOG.debug("search: " + result);
    }

};