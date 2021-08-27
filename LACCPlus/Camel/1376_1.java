//,temp,BoxCommentsManagerIT.java,133,149,temp,BoxCommentsManagerIT.java,107,118
//,3
public class xxx {
    @Test
    public void testReplyToComment() throws Exception {

        BoxComment.Info commentInfo = testFile.addComment(CAMEL_TEST_FILE_COMMENT);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.commentId", commentInfo.getID());
        // parameter type is String
        headers.put("CamelBox.message", CAMEL_TEST_FILE_REPLY_COMMENT);

        final com.box.sdk.BoxComment result = requestBodyAndHeaders("direct://REPLYTOCOMMENT", null, headers);

        assertNotNull(result, "replyToComment result");
        assertEquals(CAMEL_TEST_FILE_REPLY_COMMENT, result.getInfo().getMessage(), "replyToComment result");
        LOG.debug("replyToComment: " + result);
    }

};