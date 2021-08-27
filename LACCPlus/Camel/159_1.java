//,temp,BoxCommentsManager.java,173,185,temp,BoxFilesManager.java,503,515
//,2
public class xxx {
    public void deleteComment(String commentId) {
        try {
            LOG.debug("Deleting comment(id={})", commentId);
            if (commentId == null) {
                throw new IllegalArgumentException("Parameter 'commentId' can not be null");
            }
            BoxComment comment = new BoxComment(boxConnection, commentId);
            comment.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};