//,temp,CxfProducer.java,135,181,temp,CxfProducer.java,97,130
//,3
public class xxx {
    @Override
    public void process(Exchange camelExchange) throws Exception {
        LOG.trace("Process exchange: {} in sync way.", camelExchange);

        // create CXF exchange
        ExchangeImpl cxfExchange = new ExchangeImpl();
        // set the Bus on the exchange in case the CXF interceptor need to access it from exchange
        cxfExchange.put(Bus.class, endpoint.getBus());

        // prepare binding operation info
        BindingOperationInfo boi = prepareBindingOperation(camelExchange, cxfExchange);

        Map<String, Object> invocationContext = new HashMap<>();
        Map<String, Object> responseContext = new HashMap<>();
        invocationContext.put(Client.RESPONSE_CONTEXT, responseContext);
        invocationContext.put(Client.REQUEST_CONTEXT, prepareRequest(camelExchange, cxfExchange));

        try {
            // send the CXF request
            client.invoke(boi, getParams(endpoint, camelExchange),
                    invocationContext, cxfExchange);

        } catch (Exception exception) {
            camelExchange.setException(exception);
        } finally {
            // add cookies to the cookie store
            if (endpoint.getCookieHandler() != null) {
                try {
                    Message inMessage = cxfExchange.getInMessage();
                    if (inMessage != null) {
                        Map<String, List<String>> cxfHeaders
                                = CastUtils.cast((Map<?, ?>) inMessage.get(Message.PROTOCOL_HEADERS));
                        endpoint.getCookieHandler().storeCookies(camelExchange, endpoint.getRequestUri(camelExchange),
                                cxfHeaders);
                    }
                } catch (IOException e) {
                    LOG.warn("Cannot store cookies. This exception is ignored.", e);
                }
            }

            // bind the CXF response to Camel exchange
            if (!boi.getOperationInfo().isOneWay()) {
                endpoint.getCxfBinding().populateExchangeFromCxfResponse(camelExchange, cxfExchange,
                        responseContext);
            }
        }
    }

};