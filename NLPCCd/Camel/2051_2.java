//,temp,sample_2018.java,2,16,temp,sample_2748.java,2,16
//,2
public class xxx {
public void dummy_method(){
String originalThreadName = Thread.currentThread().getName();
Thread.currentThread().setName(createThreadName(serverSocket));
MDC.put(MDCUnitOfWork.MDC_CAMEL_CONTEXT_ID, consumer.getEndpoint().getCamelContext().getName());
Route route = consumer.getRoute();
if (route != null) {
String routeId = route.getId();
if (routeId != null) {
MDC.put(MDCUnitOfWork.MDC_ROUTE_ID, route.getId());
}
}


log.info("starting serversocket accept thread for");
}

};