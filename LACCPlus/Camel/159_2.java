//,temp,BoxCommentsManager.java,173,185,temp,BoxFilesManager.java,503,515
//,2
public class xxx {
    public void deleteFile(String fileId) {
        try {
            LOG.debug("Deleting file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            BoxFile file = new BoxFile(boxConnection, fileId);
            file.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};