//,temp,sample_5075.java,2,11,temp,sample_5074.java,2,9
//,3
public class xxx {
public Object invoke(Exchange cxfExchange, Object o) {
Continuation continuation;
if (!endpoint.isSynchronous() && isAsyncInvocationSupported(cxfExchange) && (continuation = getContinuation(cxfExchange)) != null) {


log.info("calling the camel async processors");
}
}

};