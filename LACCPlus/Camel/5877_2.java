//,temp,DefaultNettyHttpBinding.java,337,349,temp,DefaultNettyHttpBinding.java,124,137
//,3
public class xxx {
    @Override
    public Message toCamelMessage(InboundStreamHttpRequest request, Exchange exchange, NettyHttpConfiguration configuration)
            throws Exception {
        LOG.trace("toCamelMessage: {}", request);

        NettyHttpMessage answer = new NettyHttpMessage(exchange.getContext(), null, null);
        answer.setExchange(exchange);
        if (configuration.isMapHeaders()) {
            populateCamelHeaders(request.getHttpRequest(), answer.getHeaders(), exchange, configuration);
        }

        answer.setBody(request.getInputStream());
        return answer;
    }

};