//,temp,BoxFilesManagerIT.java,371,387,temp,BoxFoldersManagerIT.java,239,255
//,2
public class xxx {
    @Test
    public void testUpdateInfo() throws Exception {
        final BoxFolder.Info testFolderInfo = testFolder.getInfo();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.folderId", testFolder.getID());
        // parameter type is com.box.sdk.BoxFolder.Info
        testFolderInfo.setDescription(CAMEL_TEST_FOLDER_DESCRIPTION);
        headers.put("CamelBox.info", testFolderInfo);

        final com.box.sdk.BoxFolder result = requestBodyAndHeaders("direct://UPDATEFOLDERINFO", null, headers);

        assertNotNull(result, "updateInfo result");
        assertEquals(CAMEL_TEST_FOLDER_DESCRIPTION, result.getInfo().getDescription(), "update folder info description");
        LOG.debug("updateInfo: " + result);
    }

};