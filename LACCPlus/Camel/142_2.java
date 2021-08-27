//,temp,BoxFilesManager.java,612,626,temp,BoxFilesManager.java,588,603
//,2
public class xxx {
    public URL getDownloadURL(String fileId) {
        try {
            LOG.debug("Getting download URL for file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);

            return file.getDownloadURL();

        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};