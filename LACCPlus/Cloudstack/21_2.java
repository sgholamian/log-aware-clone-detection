//,temp,HttpTemplateDownloader.java,423,433,temp,FtpTemplateUploader.java,137,147
//,3
public class xxx {
    @Override
    public void run() {
        try {
            upload(completionCallback);
        } catch (Throwable t) {
            s_logger.warn("Caught exception during upload " + t.getMessage(), t);
            errorString = "Failed to install: " + t.getMessage();
            status = TemplateUploader.Status.UNRECOVERABLE_ERROR;
        }

    }

};