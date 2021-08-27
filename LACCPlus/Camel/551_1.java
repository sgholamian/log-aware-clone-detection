//,temp,BoxUsersManagerIT.java,221,227,temp,BoxFoldersManagerIT.java,154,162
//,3
public class xxx {
    @Test
    public void testGetCurrentUser() throws Exception {
        final com.box.sdk.BoxUser result = requestBody("direct://GETCURRENTUSER", testUser.getID());

        assertNotNull(result, "getCurrentUser result");
        LOG.debug("getCurrentUser: " + result);
    }

};