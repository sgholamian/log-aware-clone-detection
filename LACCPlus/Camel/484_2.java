//,temp,BoxCollaborationsManagerIT.java,134,141,temp,GmailUsersIT.java,40,48
//,3
public class xxx {
    @Test
    public void testGetProfile() throws Exception {
        // using String message body for single parameter "userId"
        final com.google.api.services.gmail.model.Profile result = requestBody("direct://GETPROFILE", CURRENT_USERID);

        assertNotNull(result, "getProfile result");
        assertNotNull(result.getEmailAddress(), "Should be email address associated with current account");
        LOG.debug("getProfile: " + result);
    }

};