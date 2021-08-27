//,temp,sample_1533.java,2,16,temp,sample_884.java,2,16
//,3
public class xxx {
public void dummy_method(){
context.addService(service);
context.addRoutePolicyFactory(ClusteredRoutePolicyFactory.forNamespace("my-ns"));
context.addRoutes(new RouteBuilder() {
public void configure() throws Exception {
from("timer:consul?delay=1s&period=1s") .routeId("route-" + id) .log("From ${routeId}") .process(e -> contextLatch.countDown());
}
});
Thread.sleep(ThreadLocalRandom.current().nextInt(500));
context.start();
contextLatch.await();


log.info("shutting down node");
}

};