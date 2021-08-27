//,temp,DefaultNettyHttpBinding.java,311,335,temp,DefaultNettyHttpBinding.java,92,122
//,3
public class xxx {
    @Override
    public Message toCamelMessage(FullHttpRequest request, Exchange exchange, NettyHttpConfiguration configuration)
            throws Exception {
        LOG.trace("toCamelMessage: {}", request);

        NettyHttpMessage answer = new NettyHttpMessage(exchange.getContext(), request, null);
        answer.setExchange(exchange);
        if (configuration.isMapHeaders()) {
            populateCamelHeaders(request, answer.getHeaders(), exchange, configuration);
        }

        if (configuration.isHttpProxy() || configuration.isDisableStreamCache()) {
            // keep the body as is, and use type converters
            // for proxy use case pass the request body buffer directly to the response to avoid additional processing
            // we need to retain it so that the request can be released and we can keep the content
            answer.setBody(request.content().retain());
            exchange.adapt(ExtendedExchange.class).addOnCompletion(new SynchronizationAdapter() {
                @Override
                public void onDone(Exchange exchange) {
                    ReferenceCountUtil.release(request.content());
                }
            });
        } else {
            // turn the body into stream cached (on the client/consumer side we can facade the netty stream instead of converting to byte array)
            NettyChannelBufferStreamCache cache = new NettyChannelBufferStreamCache(request.content());
            // add on completion to the cache which is needed for Camel to keep track of the lifecycle of the cache
            exchange.adapt(ExtendedExchange.class).addOnCompletion(new NettyChannelBufferStreamCacheOnCompletion(cache));
            answer.setBody(cache);
        }
        return answer;
    }

};