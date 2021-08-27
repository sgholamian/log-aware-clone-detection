//,temp,SoroushBotEndpoint.java,580,601,temp,SoroushBotEndpoint.java,545,571
//,3
public class xxx {
    public void handleDownloadFiles(SoroushMessage message) throws SoroushException {
        if (message.getFileUrl() != null && (message.getFile() == null || forceDownload)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("downloading file from server for message: " + message);
            }
            InputStream inputStream = downloadFromServer(message.getFileUrl(), message, "file");
            message.setFile(inputStream);
            if (LOG.isDebugEnabled()) {
                LOG.debug("file successfully downloaded for message: " + message);
            }
        }
        if (downloadThumbnail && message.getThumbnailUrl() != null && (message.getThumbnail() == null || forceDownload)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("downloading thumbnail from server for message: " + message);
            }
            InputStream inputStream = downloadFromServer(message.getThumbnailUrl(), message, "thumbnail");
            message.setThumbnail(inputStream);
            if (LOG.isDebugEnabled()) {
                LOG.debug("thumbnail successfully downloaded for message: " + message);
            }
        }
    }

};