//,temp,BoxFoldersManagerIT.java,164,178,temp,BoxCollaborationsManagerIT.java,60,78
//,3
public class xxx {
    @Test
    public void testAddFolderCollaborationByEmail() throws Exception {
        // delete collaborator created by setupTest
        deleteTestCollaborator();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is String
        headers.put("CamelBox.email", CAMEL_TEST_COLLABORATOR_EMAIL);
        // parameter type is com.box.sdk.BoxCollaboration.Role
        headers.put("CamelBox.role", BoxCollaboration.Role.EDITOR);

        final com.box.sdk.BoxCollaboration result = requestBodyAndHeaders("direct://ADDFOLDERCOLLABORATIONBYEMAIL",
                testFolder.getID(), headers);

        assertNotNull(result, "addFolderCollaboration result");
        LOG.debug("addFolderCollaboration: " + result);
    }

};