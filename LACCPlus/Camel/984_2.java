//,temp,BoxFoldersManager.java,347,363,temp,BoxFoldersManager.java,320,338
//,3
public class xxx {
    public BoxFolder.Info getFolderInfo(String folderId, String... fields) {
        try {
            LOG.debug("Getting info for folder(id={})", folderId);
            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }

            BoxFolder folder = new BoxFolder(boxConnection, folderId);

            if (fields == null || fields.length == 0) {
                return folder.getInfo();
            } else {
                return folder.getInfo(fields);
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};