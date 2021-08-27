//,temp,BoxCollaborationsManagerIT.java,134,141,temp,GmailUsersIT.java,40,48
//,3
public class xxx {
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetPendingCollaborations() throws Exception {
        final java.util.Collection result = requestBody("direct://GETPENDINGCOLLABORATIONS", null);

        assertNotNull(result, "getPendingCollaborations result");
        LOG.debug("getPendingCollaborations: " + result);
    }

};