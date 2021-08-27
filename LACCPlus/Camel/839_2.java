//,temp,BoxFilesManager.java,745,760,temp,BoxFilesManager.java,69,87
//,3
public class xxx {
    public BoxFile.Info getFileInfo(String fileId, String... fields) {
        try {
            LOG.debug("Getting info for file(id={})", fileId);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }

            BoxFile file = new BoxFile(boxConnection, fileId);

            if (fields == null || fields.length == 0) {
                return file.getInfo();
            } else {
                return file.getInfo(fields);
            }
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};