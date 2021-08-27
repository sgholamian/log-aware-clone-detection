//,temp,sample_2334.java,2,13,temp,sample_5949.java,2,15
//,3
public class xxx {
protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpConsumer consumer = resolve(request);
if (consumer == null) {
boolean hasAnyMethod = METHODS.stream().anyMatch(m -> getServletResolveConsumerStrategy().isHttpMethodAllowed(request, m, getConsumers()));
if (hasAnyMethod) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
return;
} else {


log.info("no consumer to service request as resource is not found");
}
}
}

};