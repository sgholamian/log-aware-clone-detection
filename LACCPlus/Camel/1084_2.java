//,temp,BoxCollaborationsManager.java,163,178,temp,BoxFilesManager.java,385,405
//,3
public class xxx {
    public BoxFileVersion promoteFileVersion(String fileId, Integer version) {
        try {
            LOG.debug("Promoting file(id={}, version={})", fileId, version);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (version == null) {
                throw new IllegalArgumentException("Parameter 'version' can not be null");
            }
            BoxFile file = new BoxFile(boxConnection, fileId);

            List<BoxFileVersion> fileVersions = (List<BoxFileVersion>) file.getVersions();
            BoxFileVersion fileVersion = fileVersions.get(version);

            fileVersion.promote();
            return fileVersion;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};