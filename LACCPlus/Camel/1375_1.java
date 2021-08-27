//,temp,BoxCommentsManagerIT.java,76,93,temp,BoxCollaborationsManagerIT.java,143,161
//,3
public class xxx {
    @Test
    public void testChangeCommentMessage() throws Exception {

        BoxComment.Info commentInfo = testFile.addComment(CAMEL_TEST_FILE_COMMENT);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.commentId", commentInfo.getID());
        // parameter type is String
        headers.put("CamelBox.message", CAMEL_TEST_FILE_CHANGED_COMMENT);

        final com.box.sdk.BoxComment result = requestBodyAndHeaders("direct://CHANGECOMMENTMESSAGE", null, headers);

        assertNotNull(result, "changeCommentMessage result");
        assertNotNull(result.getInfo().getMessage(), "changeCommentMessage message");
        assertEquals(CAMEL_TEST_FILE_CHANGED_COMMENT, result.getInfo().getMessage(), "changeCommentMessage message");
        LOG.debug("changeCommentMessage: " + result);
    }

};