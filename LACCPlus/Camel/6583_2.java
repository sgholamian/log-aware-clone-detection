//,temp,CxfClientCallback.java,83,124,temp,CxfClientCallback.java,54,81
//,3
public class xxx {
    @Override
    public void handleResponse(Map<String, Object> ctx, Object[] res) {
        try {
            super.handleResponse(ctx, res);
        } finally {
            // add cookies to the cookie store
            if (endpoint.getCookieHandler() != null && cxfExchange.getInMessage() != null) {
                try {
                    Map<String, List<String>> cxfHeaders
                            = CastUtils.cast((Map<?, ?>) cxfExchange.getInMessage().get(Message.PROTOCOL_HEADERS));
                    endpoint.getCookieHandler().storeCookies(camelExchange, endpoint.getRequestUri(camelExchange), cxfHeaders);
                } catch (IOException e) {
                    LOG.error("Cannot store cookies", e);
                }
            }
            // bind the CXF response to Camel exchange and
            // call camel callback
            // for one way messages callback is already called in 
            // process method of org.apache.camel.component.cxf.CxfProducer
            if (!boi.getOperationInfo().isOneWay()) {
                endpoint.getCxfBinding().populateExchangeFromCxfResponse(camelExchange, cxfExchange, ctx);
                camelAsyncCallback.done(false);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} calling handleResponse", Thread.currentThread().getName());
            }
        }
    }

};