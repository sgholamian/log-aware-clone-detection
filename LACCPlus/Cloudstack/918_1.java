//,temp,HttpsDirectTemplateDownloader.java,112,127,temp,HttpDirectTemplateDownloader.java,91,103
//,3
public class xxx {
    protected boolean consumeResponse(CloseableHttpResponse response) {
        s_logger.info("Downloading template " + getTemplateId() + " from " + getUrl() + " to: " + getDownloadedFilePath());
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new CloudRuntimeException("Error on HTTPS response");
        }
        try {
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            OutputStream out = new FileOutputStream(getDownloadedFilePath());
            IOUtils.copy(in, out);
        } catch (Exception e) {
            s_logger.error("Error parsing response for template " + getTemplateId() + " due to: " + e.getMessage());
            return false;
        }
        return true;
    }

};