//,temp,FhirHistoryIT.java,73,88,temp,BoxFilesManagerIT.java,116,132
//,3
public class xxx {
    @Test
    public void testCreateFileSharedLink() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is com.box.sdk.BoxSharedLink.Access
        headers.put("CamelBox.access", BoxSharedLink.Access.DEFAULT);
        // parameter type is java.util.Date
        headers.put("CamelBox.unshareDate", null);
        // parameter type is com.box.sdk.BoxSharedLink.Permissions
        headers.put("CamelBox.permissions", null);

        final com.box.sdk.BoxSharedLink result = requestBodyAndHeaders("direct://CREATEFILESHAREDLINK", null, headers);

        assertNotNull(result, "createFileSharedLink result");
        LOG.debug("createFileSharedLink: " + result);
    }

};