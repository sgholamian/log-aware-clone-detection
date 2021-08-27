//,temp,BoxFoldersManagerIT.java,224,237,temp,BoxFoldersManagerIT.java,110,133
//,3
public class xxx {
    @Test
    public void testRenameFolder() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is String
        headers.put("CamelBox.newFolderName", CAMEL_TEST_RENAME_FOLDER);

        final com.box.sdk.BoxFolder result = requestBodyAndHeaders("direct://RENAMEFOLDER", null, headers);

        assertNotNull(result, "renameFolder result");
        assertEquals(CAMEL_TEST_RENAME_FOLDER, result.getInfo().getName(), "moveFolder folder name");
        LOG.debug("renameFolder: " + result);
    }

};