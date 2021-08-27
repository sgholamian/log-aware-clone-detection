//,temp,BoxEventLogsManagerIT.java,47,69,temp,DriveFilesIT.java,220,251
//,3
public class xxx {
    @Test
    public void testUpdate1() throws Exception {

        // First retrieve the file from the API.
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // using String message body for single parameter "fileId"
        final File file = requestBody("direct://GET", fileId);

        // File's new metadata.
        file.setTitle("camel.png");

        // File's new content.
        java.io.File fileContent = new java.io.File(TEST_UPLOAD_IMG);
        FileContent mediaContent = new FileContent(null, fileContent);

        // Send the request to the API.

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is com.google.api.services.drive.model.File
        headers.put("CamelGoogleDrive.content", file);
        // parameter type is com.google.api.client.http.AbstractInputStreamContent
        headers.put("CamelGoogleDrive.mediaContent", mediaContent);

        File result = requestBodyAndHeaders("direct://UPDATE_1", null, headers);

        assertNotNull(result, "update result");
        LOG.debug("update: " + result);
    }

};