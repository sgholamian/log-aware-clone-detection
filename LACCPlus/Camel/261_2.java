//,temp,BoxUsersManager.java,257,271,temp,BoxFilesManager.java,272,287
//,2
public class xxx {
    public Collection<BoxFileVersion> getFileVersions(String fileId) {
        try {
            LOG.debug("Getting versions of file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);

            return file.getVersions();

        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};