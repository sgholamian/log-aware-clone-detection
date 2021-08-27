//,temp,DefaultNettyHttpBinding.java,337,349,temp,DefaultNettyHttpBinding.java,124,137
//,3
public class xxx {
    @Override
    public Message toCamelMessage(InboundStreamHttpResponse response, Exchange exchange, NettyHttpConfiguration configuration) {
        LOG.trace("toCamelMessage: {}", response);

        NettyHttpMessage answer = new NettyHttpMessage(exchange.getContext(), null, null);
        answer.setExchange(exchange);
        if (configuration.isMapHeaders()) {
            populateCamelHeaders(response.getHttpResponse(), answer.getHeaders(), exchange, configuration);
        }

        answer.setBody(response.getInputStream());
        return answer;
    }

};