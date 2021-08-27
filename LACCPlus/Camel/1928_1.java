//,temp,DropboxPutProducer.java,41,71,temp,DropboxGetProducer.java,39,62
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String remotePath = DropboxHelper.getRemotePath(configuration, exchange);
        String localPath = DropboxHelper.getLocalPath(configuration, exchange);
        DropboxUploadMode uploadMode = DropboxHelper.getUploadMode(configuration, exchange);

        DropboxConfigurationValidator.validatePutOp(localPath, remotePath, uploadMode);

        DropboxFileUploadResult result = new DropboxAPIFacade(configuration.getClient(), exchange)
                .put(localPath, remotePath, uploadMode);

        Map<String, DropboxResultCode> map = result.getResults();
        if (map.size() == 1) {
            for (Map.Entry<String, DropboxResultCode> entry : map.entrySet()) {
                exchange.getIn().setHeader(DropboxResultHeader.UPLOADED_FILE.name(), entry.getKey());
                exchange.getIn().setBody(entry.getValue());
            }

        } else {
            StringBuilder pathsExtracted = new StringBuilder();
            for (Map.Entry<String, DropboxResultCode> entry : map.entrySet()) {
                pathsExtracted.append(entry.getKey()).append("\n");
            }
            exchange.getIn().setHeader(DropboxResultHeader.UPLOADED_FILES.name(), pathsExtracted.toString());
            exchange.getIn().setBody(map);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Uploaded: {}", result);
        }
    }

};