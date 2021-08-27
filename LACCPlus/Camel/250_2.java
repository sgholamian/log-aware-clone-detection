//,temp,BoxGroupsManager.java,150,167,temp,BoxFilesManager.java,96,112
//,2
public class xxx {
    public BoxFile updateFileInfo(String fileId, BoxFile.Info info) {
        try {
            LOG.debug("Updating info for file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (info == null) {
                throw new IllegalArgumentException("Parameter 'info' can not be null");
            }
            BoxFile file = new BoxFile(boxConnection, fileId);
            file.updateInfo(info);
            return file;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};