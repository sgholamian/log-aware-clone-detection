//,temp,sample_2333.java,2,9,temp,sample_5948.java,2,12
//,3
public class xxx {
protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpConsumer consumer = getServletResolveConsumerStrategy().resolve(request, getConsumers());
if (consumer == null) {


log.info("no consumer to service request");
}
}

};