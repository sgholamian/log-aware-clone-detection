//,temp,DropboxScheduledPollSearchConsumer.java,40,69,temp,DropboxScheduledPollGetConsumer.java,40,75
//,3
public class xxx {
    @Override
    protected int poll() throws Exception {
        Exchange exchange = createExchange(false);
        try {
            DropboxSearchResult result = new DropboxAPIFacade(configuration.getClient(), exchange)
                    .search(configuration.getRemotePath(), configuration.getQuery());

            StringBuilder fileExtracted = new StringBuilder();
            for (SearchMatch entry : result.getFound()) {
                fileExtracted.append(entry.getMetadata().getName()).append("-").append(entry.getMetadata().getPathDisplay())
                        .append("\n");
            }

            exchange.getIn().setHeader(DropboxResultHeader.FOUND_FILES.name(), fileExtracted.toString());
            exchange.getIn().setBody(result.getFound());

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