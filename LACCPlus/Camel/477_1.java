//,temp,SubscriptionGatewayIT.java,41,49,temp,DriveFilesIT.java,105,113
//,3
public class xxx {
    @Disabled
    @Test
    public void testCancel() throws Exception {
        // using String message body for single parameter "id"
        final com.braintreegateway.Result result = requestBody("direct://CANCEL", null);

        assertNotNull(result, "cancel result");
        LOG.debug("cancel: " + result);
    }

};