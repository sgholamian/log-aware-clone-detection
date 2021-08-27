//,temp,sample_2333.java,2,9,temp,sample_5948.java,2,12
//,3
public class xxx {
protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpConsumer consumer = resolve(request);
if (consumer == null) {
boolean hasAnyMethod = METHODS.stream().anyMatch(m -> getServletResolveConsumerStrategy().isHttpMethodAllowed(request, m, getConsumers()));
if (hasAnyMethod) {


log.info("no consumer to service request as method is not allowed");
}
}
}

};