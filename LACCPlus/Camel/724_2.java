//,temp,BoxCommentsManagerIT.java,120,131,temp,BoxFilesManagerIT.java,225,232
//,3
public class xxx {
    @Test
    public void testGetDownloadURL() throws Exception {
        // using String message body for single parameter "fileId"
        final java.net.URL result = requestBody("direct://GETDOWNLOADURL", testFile.getID());

        assertNotNull(result, "getDownloadURL result");
        LOG.debug("getDownloadURL: " + result);
    }

};