//,temp,HttpsDirectTemplateDownloader.java,112,127,temp,HttpDirectTemplateDownloader.java,91,103
//,3
public class xxx {
    protected boolean performDownload() {
        s_logger.info("Downloading template " + getTemplateId() + " from " + getUrl() + " to: " + getDownloadedFilePath());
        try (
                InputStream in = request.getResponseBodyAsStream();
                OutputStream out = new FileOutputStream(getDownloadedFilePath());
        ) {
            IOUtils.copy(in, out);
        } catch (IOException e) {
            s_logger.error("Error downloading template " + getTemplateId() + " due to: " + e.getMessage());
            return false;
        }
        return true;
    }

};