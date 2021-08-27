//,temp,DriveFilesIT.java,115,121,temp,CreditCardVerificationGatewayIT.java,51,60
//,3
public class xxx {
    @Disabled
    @Test
    public void testSearch() throws Exception {
        // using com.braintreegateway.CreditCardVerificationSearchRequest message body for single parameter "query"
        final ResourceCollection<CreditCardVerification> result
                = requestBody("direct://SEARCH", null, ResourceCollection.class);

        assertNotNull(result, "search result");
        LOG.debug("search: " + result);
    }

};