//,temp,DriveCommentsIT.java,47,109,temp,DriveRepliesIT.java,46,100
//,3
public class xxx {
    @Test
    public void testReplyToComment() throws Exception {
        // 1. create test file
        File testFile = uploadTestFile();
        String fileId = testFile.getId();

        // 2. comment on that file
        Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is com.google.api.services.drive.model.Comment
        com.google.api.services.drive.model.Comment comment = new com.google.api.services.drive.model.Comment();
        comment.setContent("Camel rocks!");
        headers.put("CamelGoogleDrive.content", comment);

        requestBodyAndHeaders("direct://INSERT_COMMENT", null, headers);

        // 3. get a list of comments on the file
        // using String message body for single parameter "fileId"
        com.google.api.services.drive.model.CommentList result1 = requestBody("direct://LIST_COMMENTS", fileId);

        assertNotNull(result1.get("items"));
        LOG.debug("list: " + result1);

        Comment comment2 = result1.getItems().get(0);
        String commentId = comment2.getCommentId();

        // 4. add reply
        headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is String
        headers.put("CamelGoogleDrive.commentId", commentId);
        // parameter type is com.google.api.services.drive.model.CommentReply
        com.google.api.services.drive.model.CommentReply reply = new com.google.api.services.drive.model.CommentReply();
        reply.setContent("I know :-)");
        headers.put("CamelGoogleDrive.content", reply);

        requestBodyAndHeaders("direct://INSERT", null, headers);

        // 5. list replies on comment to file

        headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is String
        headers.put("CamelGoogleDrive.commentId", commentId);

        final com.google.api.services.drive.model.CommentReplyList result
                = requestBodyAndHeaders("direct://LIST", null, headers);

        assertNotNull(result, "list result");
        LOG.debug("list: " + result);

    }

};