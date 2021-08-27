//,temp,DriveCommentsIT.java,47,109,temp,DriveRepliesIT.java,46,100
//,3
public class xxx {
    @Test
    public void testComment() throws Exception {
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

        requestBodyAndHeaders("direct://INSERT", null, headers);

        // 3. get a list of comments on the file
        // using String message body for single parameter "fileId"
        com.google.api.services.drive.model.CommentList result1 = requestBody("direct://LIST", fileId);

        assertNotNull(result1.get("items"));
        LOG.debug("list: " + result1);

        Comment comment2 = result1.getItems().get(0);

        // 4. now try and get that comment 
        headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is String
        headers.put("CamelGoogleDrive.commentId", comment2.getCommentId());

        final com.google.api.services.drive.model.Comment result3 = requestBodyAndHeaders("direct://GET", null, headers);

        assertNotNull(result3, "get result");

        // 5. delete the comment

        headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is String
        headers.put("CamelGoogleDrive.commentId", comment2.getCommentId());

        requestBodyAndHeaders("direct://DELETE", null, headers);

        // 6. ensure the comment is gone

        headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelGoogleDrive.fileId", fileId);
        // parameter type is String
        headers.put("CamelGoogleDrive.commentId", comment2.getCommentId());

        try {
            final com.google.api.services.drive.model.Comment result4 = requestBodyAndHeaders("direct://GET", null, headers);
            fail("Should have thrown an exception.");
        } catch (Exception e) {
            // Likely safe to ignore in this context
            LOG.debug("Unhandled exception (probably safe to ignore): {}", e.getMessage(), e);
        }
    }

};