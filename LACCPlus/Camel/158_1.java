//,temp,BoxCommentsManager.java,56,73,temp,BoxFilesManager.java,480,496
//,2
public class xxx {
    public BoxFile addFileComment(String fileId, String message) {
        try {
            LOG.debug("Adding comment to file(id={}) to '{}'", fileId, message);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (message == null) {
                throw new IllegalArgumentException("Parameter 'message' can not be null");
            }

            BoxFile fileToCommentOn = new BoxFile(boxConnection, fileId);
            fileToCommentOn.addComment(message);
            return fileToCommentOn;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};