//,temp,BoxFilesManagerIT.java,183,202,temp,BoxTasksManagerIT.java,166,186
//,3
public class xxx {
    @Test
    public void testDownloadFile() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is java.io.OutputStream
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        headers.put("CamelBox.output", output);
        // parameter type is Long
        headers.put("CamelBox.rangeStart", null);
        // parameter type is Long
        headers.put("CamelBox.rangeEnd", null);
        // parameter type is com.box.sdk.ProgressListener
        headers.put("CamelBox.listener", null);

        final java.io.OutputStream result = requestBodyAndHeaders("direct://DOWNLOADFILE", null, headers);

        assertNotNull(result, "downloadFile result");
        LOG.debug("downloadFile: " + result);
    }

};