//,temp,CxfProducer.java,135,181,temp,CxfProducer.java,97,130
//,3
public class xxx {
    @Override
    public boolean process(Exchange camelExchange, AsyncCallback callback) {
        LOG.trace("Process exchange: {} in an async way.", camelExchange);

        try {
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

            CxfClientCallback cxfClientCallback = new CxfClientCallback(callback, camelExchange, cxfExchange, boi, endpoint);
            // send the CXF async request
            client.invoke(cxfClientCallback, boi, getParams(endpoint, camelExchange),
                    invocationContext, cxfExchange);
            if (boi.getOperationInfo().isOneWay()) {
                callback.done(false);
            }
        } catch (Exception ex) {
            // error occurred before we had a chance to go async
            // so set exception and invoke callback true
            camelExchange.setException(ex);
            callback.done(true);
            return true;
        }
        return false;
    }

};