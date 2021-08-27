//,temp,FhirOperationIT.java,119,140,temp,BoxFilesManagerIT.java,411,437
//,3
public class xxx {
    @Test
    public void testUploadFile() throws Exception {
        com.box.sdk.BoxFile result = null;

        try {
            final Map<String, Object> headers = new HashMap<>();
            headers.put("CamelBox.parentFolderId", "0");
            headers.put("CamelBox.content", getClass().getResourceAsStream(CAMEL_TEST_FILE));
            headers.put("CamelBox.fileName", CAMEL_TEST_UPLOAD_FILE_NAME);
            headers.put("CamelBox.created", null);
            headers.put("CamelBox.modified", null);
            headers.put("CamelBox.size", null);
            headers.put("CamelBox.listener", null);

            result = requestBodyAndHeaders("direct://UPLOADFILE", null, headers);

            assertNotNull(result, "uploadFile result");
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