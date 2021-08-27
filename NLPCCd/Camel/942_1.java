//,temp,sample_4622.java,2,16,temp,sample_6512.java,2,16
//,3
public class xxx {
public void dummy_method(){
sendMessage("Message Two");
final ServiceSupport consumer = (ServiceSupport) context.getRoute("foo").getConsumer();
await().atMost(2, TimeUnit.SECONDS).until(consumer::isSuspended);
sendMessage("Message Three");
assertMockEndpointsSatisfied();
result.reset();
result.expectedMessageCount(2);
bodies = Arrays.asList("Message Three", "Message Four");
result.expectedBodiesReceivedInAnyOrder(bodies);
await().atMost(2, TimeUnit.SECONDS).until(consumer::isStarted);


log.info("sending message four");
}

};