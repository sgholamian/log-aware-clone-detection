//,temp,BoxFilesManagerIT.java,295,303,temp,BoxGroupsManagerIT.java,120,127
//,3
public class xxx {
    @Test
    public void testGetFileVersions() throws Exception {
        // using String message body for single parameter "fileId"
        @SuppressWarnings("rawtypes")
        final java.util.Collection result = requestBody("direct://GETFILEVERSIONS", testFile.getID());

        assertNotNull(result, "getFileVersions result");
        LOG.debug("getFileVersions: " + result);
    }

};