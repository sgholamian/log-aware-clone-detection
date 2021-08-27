//,temp,DefaultNettyHttpBinding.java,311,335,temp,DefaultNettyHttpBinding.java,92,122
//,3
public class xxx {
    @Override
    public Message toCamelMessage(FullHttpResponse response, Exchange exchange, NettyHttpConfiguration configuration) {
        LOG.trace("toCamelMessage: {}", response);

        NettyHttpMessage answer = new NettyHttpMessage(exchange.getContext(), null, response);
        answer.setExchange(exchange);
        if (configuration.isMapHeaders()) {
            populateCamelHeaders(response, answer.getHeaders(), exchange, configuration);
        }

        if (configuration.isDisableStreamCache() || configuration.isHttpProxy()) {
            // keep the body as is, and use type converters
            answer.setBody(response.content());
        } else {
            // stores as byte array as the netty ByteBuf will be freed when the producer is done, and then we can no longer access the message body
            response.retain();
            try {
                byte[] bytes = exchange.getContext().getTypeConverter().convertTo(byte[].class, exchange, response.content());
                answer.setBody(bytes);
            } finally {
                response.release();
            }
        }
        return answer;
    }

};