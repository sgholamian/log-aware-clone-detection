//,temp,BoxCommentsManagerIT.java,120,131,temp,BoxFilesManagerIT.java,225,232
//,3
public class xxx {
    @Test
    public void testGetFileComments() throws Exception {
        testFile.addComment(CAMEL_TEST_FILE_COMMENT);

        // using String message body for single parameter "fileId"
        @SuppressWarnings("rawtypes")
        final java.util.List result = requestBody("direct://GETFILECOMMENTS", testFile.getID());

        assertNotNull(result, "getFileComments result");
        assertEquals(1, result.size(), "getFileComments size");
        LOG.debug("getFileComments: " + result);
    }

};