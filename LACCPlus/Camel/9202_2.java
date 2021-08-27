//,temp,SoroushBotEndpoint.java,580,601,temp,SoroushBotEndpoint.java,545,571
//,3
public class xxx {
    void handleFileUpload(SoroushMessage message) throws SoroushException, InterruptedException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("try to upload file(s) to server if exists for message:" + message.toString());
        }
        InputStream file = message.getFile();
        if (file != null && (message.getFileUrl() == null || forceUpload)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("uploading file to server for message: " + message);
            }
            UploadFileResponse response = uploadToServer(file, message, "file");
            message.setFileUrl(response.getFileUrl());
            if (LOG.isDebugEnabled()) {
                LOG.debug("uploaded file url is: " + response.getFileUrl() + " for message: " + message);
            }
        }
        InputStream thumbnail = message.getThumbnail();
        if (thumbnail != null && message.getThumbnailUrl() == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("uploading thumbnail to server for message: " + message);
            }
            UploadFileResponse response = uploadToServer(thumbnail, message, "thumbnail");
            message.setThumbnailUrl(response.getFileUrl());
            if (LOG.isDebugEnabled()) {
                LOG.debug("uploaded thumbnail url is: " + response.getFileUrl() + " for message: " + message);
            }
        }
    }

};