//,temp,BoxFoldersManagerIT.java,207,222,temp,DriveFilesIT.java,54,73
//,3
public class xxx {
    @Test
    public void testCopy() throws Exception {
        File testFile = uploadTestFile();
        String fromFileId = testFile.getId();

        File toFile = new File();
        toFile.setTitle(UPLOAD_FILE.getName() + "_copy");

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fromFileId);
        // parameter type is com.google.api.services.drive.model.File
        headers.put("CamelGoogleDrive.content", toFile);

        final File result = requestBodyAndHeaders("direct://COPY", null, headers);

        assertNotNull(result, "copy result");
        assertEquals(toFile.getTitle(), result.getTitle());
        LOG.debug("copy: " + result);
    }

};