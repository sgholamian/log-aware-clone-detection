//,temp,BoxFoldersManager.java,179,203,temp,BoxFoldersManager.java,76,101
//,3
public class xxx {
    public BoxFolder getFolder(String... path) {
        try {
            LOG.debug("Getting folder at path={}", Arrays.toString(path));

            BoxFolder folder = BoxFolder.getRootFolder(boxConnection);
            if (path == null || path.length == 0) {
                // Return root folder if path is null or empty.
                return folder;
            }

            searchPath: for (int folderIndex = 0; folderIndex < path.length; folderIndex++) {
                for (BoxItem.Info itemInfo : folder) {
                    if (itemInfo instanceof BoxFolder.Info && itemInfo.getName().equals(path[folderIndex])) {
                        folder = (BoxFolder) itemInfo.getResource();
                        continue searchPath;
                    }
                }
                // Failed to find named folder in path: return null
                return null;
            }
            return folder;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};