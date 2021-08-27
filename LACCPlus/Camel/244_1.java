//,temp,BoxFilesManagerIT.java,371,387,temp,BoxFoldersManagerIT.java,239,255
//,2
public class xxx {
    @Test
    public void testUpdateFileInfo() throws Exception {
        BoxFile.Info info = testFile.getInfo();
        info.setDescription(CAMEL_TEST_FILE_DESCRIPTION);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is com.box.sdk.BoxFile.Info
        headers.put("CamelBox.info", info);

        final com.box.sdk.BoxFile result = requestBodyAndHeaders("direct://UPDATEFILEINFO", null, headers);

        assertNotNull(result, "updateFileInfo result");
        assertEquals(CAMEL_TEST_FILE_DESCRIPTION, result.getInfo().getDescription(), "updateFileInfo info");
        LOG.debug("updateFileInfo: " + result);
    }

};