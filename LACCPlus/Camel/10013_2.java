//,temp,NettyProducer.java,349,387,temp,ServerResponseFutureListener.java,49,81
//,3
public class xxx {
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        // if it was not a success then thrown an exception
        if (!future.isSuccess()) {
            Exception e = new CamelExchangeException("Cannot write response to " + remoteAddress, exchange, future.cause());
            consumer.getExceptionHandler().handleException(e);
        }

        // should channel be closed after complete?
        Boolean close;
        if (exchange.hasOut()) {
            close = exchange.getOut().getHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, Boolean.class);
        } else {
            close = exchange.getIn().getHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, Boolean.class);
        }

        // check the setting on the exchange property
        if (close == null) {
            close = exchange.getProperty(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, Boolean.class);
        }

        // should we disconnect, the header can override the configuration
        boolean disconnect = consumer.getConfiguration().isDisconnect();
        if (close != null) {
            disconnect = close;
        }
        if (disconnect) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Closing channel when complete at address: {}", remoteAddress);
            }
            NettyHelper.close(future.channel());
        }
    }

};