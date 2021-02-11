//,temp,TemplateDownloaderBase.java,131,140,temp,FtpTemplateUploader.java,137,147
//,3
public class xxx {
    @Override
    protected void runInContext() {
        try {
            download(_resume, _callback);
        } catch (Exception e) {
            s_logger.warn("Unable to complete download due to ", e);
            _errorString = "Failed to install: " + e.getMessage();
            _status = TemplateDownloader.Status.UNRECOVERABLE_ERROR;
        }
    }

};