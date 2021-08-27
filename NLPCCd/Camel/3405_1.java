//,temp,sample_2334.java,2,13,temp,sample_5949.java,2,15
//,3
public class xxx {
protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpConsumer consumer = getServletResolveConsumerStrategy().resolve(request, getConsumers());
if (consumer == null) {
response.sendError(HttpServletResponse.SC_NOT_FOUND);
return;
}
if (consumer.isSuspended()) {


log.info("consumer suspended cannot service request");
}
}

};