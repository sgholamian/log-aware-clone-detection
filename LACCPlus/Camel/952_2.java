//,temp,BoxCommentsManager.java,151,166,temp,BoxFilesManager.java,524,543
//,3
public class xxx {
    public void deleteFileVersion(String fileId, Integer version) {
        try {
            LOG.debug("Deleting file(id={}, version={})", fileId, version);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (version == null) {
                throw new IllegalArgumentException("Parameter 'version' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);
            List<BoxFileVersion> versions = (List<BoxFileVersion>) file.getVersions();
            BoxFileVersion fileVersion = versions.get(version);

            fileVersion.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};