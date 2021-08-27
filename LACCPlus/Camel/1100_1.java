//,temp,BoxFoldersManagerIT.java,164,178,temp,BoxCollaborationsManagerIT.java,60,78
//,3
public class xxx {
    @Test
    public void testGetFolderInfo() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is String[]
        headers.put("CamelBox.fields", new String[] { "name" });

        final com.box.sdk.BoxFolder.Info result = requestBodyAndHeaders("direct://GETFOLDERINFO", null, headers);

        assertNotNull(result, "getFolderInfo result");
        assertNotNull(result.getName(), "getFolderInfo result.getName()");
        assertEquals(CAMEL_TEST_FOLDER, result.getName(), "getFolderInfo info name");
        LOG.debug("getFolderInfo: " + result);
    }

};