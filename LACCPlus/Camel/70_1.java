//,temp,BoxUsersManagerIT.java,239,246,temp,BoxCollaborationsManagerIT.java,114,122
//,2
public class xxx {
    @Test
    public void testGetUserInfo() throws Exception {
        // using String message body for single parameter "userId"
        final com.box.sdk.BoxUser.Info result = requestBody("direct://GETUSERINFO", testUser.getID());

        assertNotNull(result, "getUserInfo result");
        LOG.debug("getUserInfo: " + result);
    }

};