//,temp,BoxFilesManagerIT.java,264,271,temp,AccountIT.java,64,73
//,3
public class xxx {
    @Test
    public void testGetFilePreviewLink() throws Exception {
        // using String message body for single parameter "fileId"
        final java.net.URL result = requestBody("direct://GETFILEPREVIEWLINK", testFile.getID());

        assertNotNull(result, "getFilePreviewLink result");
        LOG.debug("getFilePreviewLink: " + result);
    }

};