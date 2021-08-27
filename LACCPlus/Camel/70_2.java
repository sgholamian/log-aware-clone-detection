//,temp,BoxUsersManagerIT.java,239,246,temp,BoxCollaborationsManagerIT.java,114,122
//,2
public class xxx {
    @Test
    public void testGetCollaborationInfo() throws Exception {
        // using String message body for single parameter "collaborationId"
        final com.box.sdk.BoxCollaboration.Info result = requestBody("direct://GETCOLLABORATIONINFO",
                testCollaboration.getID());

        assertNotNull(result, "getCollaborationInfo result");
        LOG.debug("getCollaborationInfo: " + result);
    }

};