//,temp,BoxUsersManager.java,232,249,temp,BoxFoldersManager.java,376,399
//,3
public class xxx {
    public BoxSharedLink createFolderSharedLink(
            String folderId, BoxSharedLink.Access access, Date unshareDate,
            BoxSharedLink.Permissions permissions) {
        try {
            LOG.debug("Creating shared link for folder(id={}) with access={} {}",
                    folderId, access, unshareDate == null
                            ? ""
                            : " unsharedDate=" + DateFormat.getDateTimeInstance().format(unshareDate)
                              + " permissions=" + permissions);

            if (folderId == null) {
                throw new IllegalArgumentException("Parameter 'folderId' can not be null");
            }
            if (access == null) {
                throw new IllegalArgumentException("Parameter 'access' can not be null");
            }

            BoxFolder folder = new BoxFolder(boxConnection, folderId);
            return folder.createSharedLink(access, unshareDate, permissions);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};