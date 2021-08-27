//,temp,BoxCommentsManagerIT.java,59,74,temp,BoxFilesManagerIT.java,389,409
//,3
public class xxx {
    @Test
    public void testAddFileComment() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.fileId", testFile.getID());
        // parameter type is String
        headers.put("CamelBox.message", CAMEL_TEST_FILE_COMMENT);

        final com.box.sdk.BoxFile result = requestBodyAndHeaders("direct://ADDFILECOMMENT", null, headers);

        assertNotNull(result, "addFileComment result");
        assertNotNull(result.getComments(), "addFileComment comments");
        assertTrue(result.getComments().size() > 0, "changeCommentMessage comments size");
        assertEquals(CAMEL_TEST_FILE_COMMENT, result.getComments().get(0).getMessage(), "changeCommentMessage comment message");
        LOG.debug("addFileComment: " + result);
    }

};