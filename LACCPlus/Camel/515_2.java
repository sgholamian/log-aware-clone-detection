//,temp,BoxFoldersManagerIT.java,199,205,temp,SubscriptionGatewayIT.java,79,87
//,3
public class xxx {
    @Disabled
    @Test
    public void testFind() throws Exception {
        // using String message body for single parameter "id"
        final com.braintreegateway.Subscription result = requestBody("direct://FIND", null);

        assertNotNull(result, "find result");
        LOG.debug("find: " + result);
    }

};