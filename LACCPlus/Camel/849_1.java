//,temp,DriveFilesIT.java,204,218,temp,FhirMetaIT.java,127,140
//,3
public class xxx {
    @Test
    public void testUpdate() throws Exception {
        File theTestFile = uploadTestFile();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", theTestFile.getId());
        // parameter type is com.google.api.services.drive.model.File
        headers.put("CamelGoogleDrive.content", theTestFile);

        File result = requestBodyAndHeaders("direct://UPDATE", null, headers);

        assertNotNull(result, "update result");
        LOG.debug("update: " + result);
    }

};