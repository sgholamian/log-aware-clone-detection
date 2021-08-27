//,temp,BoxTasksManagerIT.java,64,80,temp,DriveFilesIT.java,254,267
//,3
public class xxx {
    @Disabled
    @Test
    public void testWatch() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", null);
        // parameter type is com.google.api.services.drive.model.Channel
        headers.put("CamelGoogleDrive.contentChannel", null);

        final com.google.api.services.drive.Drive.Files.Watch result = requestBodyAndHeaders("direct://WATCH", null, headers);

        assertNotNull(result, "watch result");
        LOG.debug("watch: " + result);
    }

};