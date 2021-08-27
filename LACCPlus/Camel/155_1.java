//,temp,BoxCommentsManager.java,104,118,temp,BoxTasksManager.java,231,245
//,2
public class xxx {
    public BoxComment.Info getCommentInfo(String commentId) {
        try {
            LOG.debug("Getting info for comment(id={})", commentId);
            if (commentId == null) {
                throw new IllegalArgumentException("Parameter 'commentId' can not be null");
            }

            BoxComment comment = new BoxComment(boxConnection, commentId);

            return comment.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};