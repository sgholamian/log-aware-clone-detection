//,temp,DriveFilesIT.java,93,103,temp,DriveFilesIT.java,75,91
//,3
public class xxx {
    @Test
    public void testDelete() throws Exception {
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // using String message body for single parameter "fileId"
        sendBody("direct://DELETE", fileId);

        try {
            // the file should be gone now
            final File result = requestBody("direct://GET", fileId);
            fail("Should have not found deleted file.");
        } catch (Exception e) {
            // Likely safe to ignore in this context
            LOG.debug("Unhandled exception (probably safe to ignore): {}", e.getMessage(), e);
        }
    }

};