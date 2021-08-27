//,temp,BoxFilesManagerIT.java,468,497,temp,BoxFilesManagerIT.java,439,466
//,3
public class xxx {
    @Test
    public void testUploadNewFileVersion() throws Exception {
        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.fileId", testFile.getID());
            // parameter type is java.io.InputStream
            headers.put("CamelBox.fileContent", getClass().getResourceAsStream(CAMEL_TEST_FILE));
            // parameter type is java.util.Date
            headers.put("CamelBox.modified", null);
            // parameter type is Long
            headers.put("CamelBox.fileSize", null);
            // parameter type is com.box.sdk.ProgressListener
            headers.put("CamelBox.listener", null);

            result = requestBodyAndHeaders("direct://UPLOADNEWFILEVERSION", null, headers);

            assertNotNull(result, "uploadNewFileVersion result");
            LOG.debug("uploadNewFileVersion: " + result);
        } finally {
            if (result != null) {
                try {
                    result.delete();
                } catch (Throwable t) {
                }
            }
        }
    }

};