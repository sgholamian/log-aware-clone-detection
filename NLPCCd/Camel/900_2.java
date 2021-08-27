//,temp,sample_3158.java,2,16,temp,sample_6020.java,2,16
//,3
public class xxx {
public void dummy_method(){
context.addService(createClusterService(id, address));
context.addRoutePolicyFactory(ClusteredRoutePolicyFactory.forNamespace("my-ns"));
context.addRoutes(new RouteBuilder() {
public void configure() throws Exception {
from("timer:atomix?delay=1s&period=1s") .routeId("route-" + id) .log("From ${routeId}") .process(e -> contextLatch.countDown());
}
});
Thread.sleep(ThreadLocalRandom.current().nextInt(500));
context.start();
contextLatch.await();


log.info("shutting down client node");
}

};