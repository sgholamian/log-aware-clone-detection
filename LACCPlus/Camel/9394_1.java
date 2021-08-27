//,temp,BoxFoldersManager.java,179,203,temp,BoxFoldersManager.java,76,101
//,3
public class xxx {
    public BoxFolder createFolder(String parentFolderId, String... path) {
        try {
            LOG.debug("Creating folder with path '{}' in parent_folder(id={})", Arrays.toString(path), parentFolderId);
            if (parentFolderId == null) {
                throw new IllegalArgumentException("Parameter 'parentFolderId' can not be null");
            }
            if (path == null) {
                throw new IllegalArgumentException("Paramerer 'path' can not be null");
            }
            BoxFolder folder = new BoxFolder(boxConnection, parentFolderId);
            searchPath: for (int folderIndex = 0; folderIndex < path.length; folderIndex++) {
                for (BoxItem.Info itemInfo : folder) {
                    if (itemInfo instanceof BoxFolder.Info && itemInfo.getName().equals(path[folderIndex])) {
                        folder = (BoxFolder) itemInfo.getResource();
                        continue searchPath;
                    }
                }
                folder = folder.createFolder(path[folderIndex]).getResource();
            }
            return folder;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};