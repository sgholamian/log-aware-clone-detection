//,temp,BoxCommentsManagerIT.java,133,149,temp,BoxCommentsManagerIT.java,107,118
//,3
public class xxx {
    @Test
    public void testGetCommentInfo() throws Exception {

        BoxComment.Info commentInfo = testFile.addComment(CAMEL_TEST_FILE_COMMENT);

        // using String message body for single parameter "commentId"
        final com.box.sdk.BoxComment.Info result = requestBody("direct://GETCOMMENTINFO", commentInfo.getID());

        assertNotNull(result, "getCommentInfo result");
        assertEquals(CAMEL_TEST_FILE_COMMENT, result.getMessage(), "getCommentInfo message");
        LOG.debug("getCommentInfo: " + result);
    }

};