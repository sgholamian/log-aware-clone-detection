//,temp,NettyProducer.java,285,307,temp,MinaProducer.java,195,217
//,3
public class xxx {
    protected void maybeDisconnectOnDone(Exchange exchange) throws InterruptedException {
        if (session == null) {
            return;
        }

        // should session be closed after complete?
        Boolean close;
        if (ExchangeHelper.isOutCapable(exchange)) {
            close = exchange.getOut().getHeader(MinaConstants.MINA_CLOSE_SESSION_WHEN_COMPLETE, Boolean.class);
        } else {
            close = exchange.getIn().getHeader(MinaConstants.MINA_CLOSE_SESSION_WHEN_COMPLETE, Boolean.class);
        }

        // should we disconnect, the header can override the configuration
        boolean disconnect = getEndpoint().getConfiguration().isDisconnect();
        if (close != null) {
            disconnect = close;
        }
        if (disconnect) {
            LOG.debug("Closing session when complete at address: {}", address);
            closeSessionIfNeededAndAwaitCloseInHandler(session);
        }
    }

};