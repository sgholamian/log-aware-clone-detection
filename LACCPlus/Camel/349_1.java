//,temp,DriveFilesIT.java,93,103,temp,DriveFilesIT.java,75,91
//,3
public class xxx {
    @Test
    public void testGet() throws Exception {
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // using String message body for single parameter "fileId"
        final File result = requestBody("direct://GET", fileId);

        assertNotNull(result, "get result");
        LOG.debug("get: " + result);
    }

};