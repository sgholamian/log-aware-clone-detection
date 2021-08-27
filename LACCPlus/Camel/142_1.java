//,temp,BoxFilesManager.java,612,626,temp,BoxFilesManager.java,588,603
//,2
public class xxx {
    public URL getFilePreviewLink(String fileId) {
        try {
            LOG.debug("Getting preview link for file(id={})", fileId);

            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);
            return file.getPreviewLink();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};