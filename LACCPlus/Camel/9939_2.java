//,temp,BoxFilesManagerIT.java,468,497,temp,BoxFilesManagerIT.java,439,466
//,3
public class xxx {
    @Test
    public void testUploadOverwriteFile() throws Exception {
        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("CamelBox.parentFolderId", "0");
            headers.put("CamelBox.content", getClass().getResourceAsStream(CAMEL_TEST_FILE));
            headers.put("CamelBox.fileName", CAMEL_TEST_UPLOAD_FILE_NAME);
            headers.put("CamelBox.created", null);
            headers.put("CamelBox.modified", null);
            headers.put("CamelBox.size", null);
            headers.put("CamelBox.listener", null);

            result = requestBodyAndHeaders("direct://UPLOADFILE", null, headers);
            assertNotNull(result, "uploadFile result");
            result = requestBodyAndHeaders("direct://UPLOADFILEOVERWRITE", null, headers);
            assertNotNull(result, "uploadFile overwrite result");
            LOG.debug("uploadFile: " + result);
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