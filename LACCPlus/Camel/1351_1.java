//,temp,BoxFilesManagerIT.java,305,328,temp,BoxFilesManagerIT.java,248,262
//,3
public class xxx {
    @Test
    public void testMoveFile() throws Exception {
        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.fileId", testFile.getID());
            // parameter type is String
            headers.put("CamelBox.destinationFolderId", "0");
            // parameter type is String
            headers.put("CamelBox.newName", CAMEL_TEST_MOVE_FILE_NAME);

            result = requestBodyAndHeaders("direct://MOVEFILE", null, headers);

            assertNotNull(result, "moveFile result");
            assertEquals(CAMEL_TEST_MOVE_FILE_NAME, result.getInfo().getName(), "moveFile name");
            LOG.debug("moveFile: " + result);
        } finally {
            if (result != null) {
                result.delete();
            }
        }
    }

};