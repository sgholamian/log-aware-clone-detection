//,temp,SubscriptionGatewayIT.java,52,60,temp,CreditCardVerificationGatewayIT.java,40,48
//,3
public class xxx {
    @Disabled
    @Test
    public void testFind() throws Exception {
        // using String message body for single parameter "id"
        final CreditCardVerification result = requestBody("direct://FIND", null, CreditCardVerification.class);

        assertNotNull(result, "find result");
        LOG.debug("find: " + result);
    }

};