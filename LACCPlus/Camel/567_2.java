//,temp,BoxUsersManagerIT.java,229,237,temp,SubscriptionGatewayIT.java,117,125
//,3
public class xxx {
    @Disabled
    @Test
    public void testSearch() throws Exception {
        // using com.braintreegateway.SubscriptionSearchRequest message body for single parameter "searchRequest"
        final com.braintreegateway.ResourceCollection result = requestBody("direct://SEARCH", null);

        assertNotNull(result, "search result");
        LOG.debug("search: " + result);
    }

};