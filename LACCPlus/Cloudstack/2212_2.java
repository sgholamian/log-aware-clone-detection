//,temp,TemplateDownloaderBase.java,131,140,temp,HttpTemplateDownloader.java,423,433
//,3
public class xxx {
    @Override
    protected void runInContext() {
        try {
            download(resume, completionCallback);
        } catch (Throwable t) {
            s_logger.warn("Caught exception during download " + t.getMessage(), t);
            errorString = "Failed to install: " + t.getMessage();
            status = TemplateDownloader.Status.UNRECOVERABLE_ERROR;
        }

    }

};