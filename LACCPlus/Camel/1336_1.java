//,temp,BoxFilesManagerIT.java,204,223,temp,BoxFilesManagerIT.java,71,94
//,3
public class xxx {
    @Disabled // Requires premium user account to test
    @Test
    public void testDownloadPreviousFileVersion() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is Integer
        headers.put("CamelBox.version", 0);
        // parameter type is java.io.OutputStream
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        headers.put("CamelBox.output", output);
        // parameter type is com.box.sdk.ProgressListener
        headers.put("CamelBox.listener", null);

        final java.io.OutputStream result = requestBodyAndHeaders("direct://DOWNLOADPREVIOUSFILEVERSION", null,
                headers);

        assertNotNull(result, "downloadPreviousFileVersion result");
        LOG.debug("downloadPreviousFileVersion: " + result);
    }

};