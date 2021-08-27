//,temp,DropboxScheduledPollSearchConsumer.java,40,69,temp,DropboxScheduledPollGetConsumer.java,40,75
//,3
public class xxx {
    @Override
    protected int poll() throws Exception {
        Exchange exchange = createExchange(false);
        try {
            DropboxFileDownloadResult result = new DropboxAPIFacade(configuration.getClient(), exchange)
                    .get(configuration.getRemotePath());

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

            if (LOG.isDebugEnabled()) {
                LOG.debug("Downloaded: {}", result);
            }
            // send message to next processor in the route
            getProcessor().process(exchange);
            return 1; // number of messages polled
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
            releaseExchange(exchange, false);
        }
    }

};