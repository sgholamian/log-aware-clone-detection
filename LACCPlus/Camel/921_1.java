//,temp,BoxCommentsManager.java,127,142,temp,BoxTasksManager.java,84,103
//,3
public class xxx {
    public BoxComment replyToComment(String commentId, String message) {
        try {
            LOG.debug("Replying to comment(id={}) with message={}", commentId, message);
            if (commentId == null) {
                throw new IllegalArgumentException("Parameter 'commentId' can not be null");
            }
            if (message == null) {
                throw new IllegalArgumentException("Parameter 'message' can not be null");
            }
            BoxComment comment = new BoxComment(boxConnection, commentId);
            return comment.reply(message).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};