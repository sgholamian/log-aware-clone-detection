//,temp,CxfClientCallback.java,83,124,temp,CxfClientCallback.java,54,81
//,3
public class xxx {
    @Override
    public void handleException(Map<String, Object> ctx, Throwable ex) {
        try {
            super.handleException(ctx, ex);
            // need to call the conduitSelector complete method to enable the fail over feature
            ConduitSelector conduitSelector = cxfExchange.get(ConduitSelector.class);
            if (conduitSelector != null) {
                conduitSelector.complete(cxfExchange);
                ex = cxfExchange.getOutMessage().getContent(Exception.class);
                if (ex == null && cxfExchange.getInMessage() != null) {
                    ex = cxfExchange.getInMessage().getContent(Exception.class);
                }
                if (ex != null) {
                    camelExchange.setException(ex);
                }
            } else {
                camelExchange.setException(ex);
            }
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
            // copy the context information and 
            // call camel callback
            // for one way messages callback is already called in 
            // process method of org.apache.camel.component.cxf.CxfProducer
            if (!boi.getOperationInfo().isOneWay()) {
                endpoint.getCxfBinding().populateExchangeFromCxfResponse(camelExchange, cxfExchange, ctx);
                camelAsyncCallback.done(false);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} calling handleException", Thread.currentThread().getName());
            }
        }
    }

};