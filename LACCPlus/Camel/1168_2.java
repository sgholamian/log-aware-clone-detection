//,temp,FhirPatchIT.java,97,113,temp,BoxFoldersManagerIT.java,135,152
//,3
public class xxx {
    @Test
    public void testCreateSharedLink() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is com.box.sdk.BoxSharedLink.Access
        headers.put("CamelBox.access", BoxSharedLink.Access.COLLABORATORS);
        // parameter type is java.util.Date
        headers.put("CamelBox.unshareDate", null);
        // parameter type is com.box.sdk.BoxSharedLink.Permissions
        headers.put("CamelBox.permissions", new BoxSharedLink.Permissions());

        final com.box.sdk.BoxSharedLink result = requestBodyAndHeaders("direct://CREATEFOLDERSHAREDLINK", null,
                headers);

        assertNotNull(result, "createFolderSharedLink result");
        LOG.debug("createFolderSharedLink: " + result);
    }

};