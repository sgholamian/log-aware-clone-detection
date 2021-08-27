//,temp,BoxCommentsManager.java,81,96,temp,BoxTasksManager.java,58,73
//,2
public class xxx {
    public List<BoxComment.Info> getFileComments(String fileId) {
        try {
            LOG.debug("Getting comments of file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);

            return file.getComments();

        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};