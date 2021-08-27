//,temp,BoxGroupsManager.java,82,99,temp,BoxFilesManager.java,556,579
//,3
public class xxx {
    public BoxSharedLink createFileSharedLink(
            String fileId, BoxSharedLink.Access access, Date unshareDate,
            BoxSharedLink.Permissions permissions) {
        try {
            LOG.debug("Creating shared link for file(id={}) with access={} {}",
                    fileId, access, unshareDate == null
                            ? ""
                            : " unsharedDate=" + DateFormat.getDateTimeInstance().format(unshareDate)
                              + " permissions=" + permissions);

            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (access == null) {
                throw new IllegalArgumentException("Parameter 'access' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);
            return file.createSharedLink(access, unshareDate, permissions);
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};