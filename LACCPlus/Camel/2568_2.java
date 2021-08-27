//,temp,CxfConsumer.java,170,220,temp,CxfRsInvoker.java,85,136
//,3
public class xxx {
    private Object asyncInvoke(
            Exchange cxfExchange, final Object serviceObject, Method method,
            Object[] paramArray, final Continuation continuation, Object response)
            throws Exception {
        synchronized (continuation) {
            if (continuation.isNew()) {
                final org.apache.camel.Exchange camelExchange = prepareExchange(cxfExchange, method, paramArray, response);
                // we want to handle the UoW
                cxfRsConsumer.createUoW(camelExchange);
                // Now we don't set up the timeout value
                LOG.trace("Suspending continuation of exchangeId: {}", camelExchange.getExchangeId());
                // The continuation could be called before the suspend is called
                continuation.suspend(endpoint.getContinuationTimeout());
                cxfExchange.put(SUSPENED, Boolean.TRUE);
                continuation.setObject(camelExchange);
                cxfRsConsumer.getAsyncProcessor().process(camelExchange, new AsyncCallback() {
                    public void done(boolean doneSync) {
                        // make sure the continuation resume will not be called before the suspend method in other thread
                        synchronized (continuation) {
                            LOG.trace("Resuming continuation of exchangeId: {}", camelExchange.getExchangeId());
                            // resume processing after both, sync and async callbacks
                            continuation.resume();
                        }
                    }
                });
                return null;
            }
            if (!continuation.isTimeout() && continuation.isResumed()) {
                cxfExchange.put(SUSPENED, Boolean.FALSE);
                org.apache.camel.Exchange camelExchange = (org.apache.camel.Exchange) continuation.getObject();
                try {
                    return returnResponse(cxfExchange, camelExchange);
                } catch (Exception ex) {
                    cxfRsConsumer.doneUoW(camelExchange);
                    throw ex;
                }
            } else {
                if (continuation.isTimeout() || !continuation.isPending()) {
                    cxfExchange.put(SUSPENED, Boolean.FALSE);
                    org.apache.camel.Exchange camelExchange = (org.apache.camel.Exchange) continuation.getObject();
                    camelExchange.setException(new ExchangeTimedOutException(camelExchange, endpoint.getContinuationTimeout()));
                    try {
                        return returnResponse(cxfExchange, camelExchange);
                    } catch (Exception ex) {
                        cxfRsConsumer.doneUoW(camelExchange);
                        throw ex;
                    }
                }
            }
        }
        return null;
    }

};