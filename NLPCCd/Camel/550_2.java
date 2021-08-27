//,temp,sample_4220.java,2,13,temp,sample_2074.java,2,13
//,3
public class xxx {
private MethodInfo chooseMethodWithMatchingBody(Exchange exchange, Collection<MethodInfo> operationList, List<MethodInfo> operationsWithCustomAnnotation) throws AmbiguousMethodCallException {
Message in = exchange.getIn();
Object body = in.getBody();
if (body != null) {
Class<?> bodyType = body.getClass();
if (LOG.isTraceEnabled()) {


log.info("matching for method with a single parameter that matches type");
}
}
}

};