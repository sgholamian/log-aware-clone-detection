//,temp,BoxUsersManagerIT.java,229,237,temp,SubscriptionGatewayIT.java,117,125
//,3
public class xxx {
    @Test
    public void testGetUserEmailAlias() throws Exception {
        // using String message body for single parameter "userId"
        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBody("direct://GETUSEREMAILALIAS", testUser.getID());

        assertNotNull(result, "getUserEmailAlias result");
        LOG.debug("getUserEmailAlias: " + result);
    }

};