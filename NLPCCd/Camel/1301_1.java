//,temp,sample_6971.java,2,16,temp,sample_3588.java,2,16
//,3
public class xxx {
public void dummy_method(){
context.setName("context-" + id);
context.addService(createClusterService(id, address));
context.addRoutes(new RouteBuilder() {
public void configure() throws Exception {
from("timer:atomix?delay=1s&period=1s") .routeId("route-" + id) .routePolicy(ClusteredRoutePolicy.forNamespace("my-ns")) .log("From ${routeId}") .process(e -> contextLatch.countDown());
}
});
Thread.sleep(ThreadLocalRandom.current().nextInt(500));
context.start();
contextLatch.await();


log.info("shutting down client node");
}

};