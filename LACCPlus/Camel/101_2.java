//,temp,BoxGroupsManagerIT.java,170,178,temp,BoxCollaborationsManagerIT.java,124,132
//,2
public class xxx {
    @Test
    public void testGetFolderCollaborations() throws Exception {
        // using String message body for single parameter "folderId"
        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBody("direct://GETFOLDERCOLLABORATIONS", testFolder.getID());

        assertNotNull(result, "getFolderCollaborations result");
        LOG.debug("getFolderCollaborations: " + result);
    }

};