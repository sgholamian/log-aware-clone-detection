//,temp,BoxCommentsManager.java,151,166,temp,BoxFilesManager.java,524,543
//,3
public class xxx {
    public BoxComment changeCommentMessage(String commentId, String message) {
        try {
            LOG.debug("Changing comment(id={}) message={}", commentId, message);
            if (commentId == null) {
                throw new IllegalArgumentException("Parameter 'commentId' can not be null");
            }
            if (message == null) {
                throw new IllegalArgumentException("Parameter 'message' can not be null");
            }
            BoxComment comment = new BoxComment(boxConnection, commentId);
            return comment.changeMessage(message).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};