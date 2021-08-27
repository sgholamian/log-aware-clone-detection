//,temp,DropboxPutProducer.java,41,71,temp,DropboxGetProducer.java,39,62
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {
        String remotePath = DropboxHelper.getRemotePath(configuration, exchange);
        DropboxConfigurationValidator.validateGetOp(remotePath);

        DropboxFileDownloadResult result = new DropboxAPIFacade(configuration.getClient(), exchange)
                .get(remotePath);

        Map<String, Object> map = result.getEntries();
        if (map.size() == 1) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                exchange.getIn().setHeader(DropboxResultHeader.DOWNLOADED_FILE.name(), entry.getKey());
                exchange.getIn().setBody(entry.getValue());
            }
        } else {
            StringBuilder pathsExtracted = new StringBuilder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                pathsExtracted.append(entry.getKey()).append("\n");
            }
            exchange.getIn().setHeader(DropboxResultHeader.DOWNLOADED_FILES.name(), pathsExtracted.toString());
            exchange.getIn().setBody(map);
        }
        LOG.debug("Downloaded: {}", result);
    }

};