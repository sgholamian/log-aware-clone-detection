//,temp,NettyProducer.java,285,307,temp,MinaProducer.java,195,217
//,3
public class xxx {
                @Override
                public void onComplete(Exchange exchange) {
                    // should channel be closed after complete?
                    Boolean close;
                    if (ExchangeHelper.isOutCapable(exchange)) {
                        close = exchange.getOut().getHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, Boolean.class);
                    } else {
                        close = exchange.getIn().getHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, Boolean.class);
                    }

                    // should we disconnect, the header can override the configuration
                    boolean disconnect = getConfiguration().isDisconnect();
                    if (close != null) {
                        disconnect = close;
                    }

                    if (disconnect) {
                        LOG.trace("Closing channel {} as routing the Exchange is done", channel);
                        NettyHelper.close(channel);
                    }

                    releaseChannel(channelFuture);
                }

};