//,temp,sample_4621.java,2,16,temp,sample_2653.java,2,16
//,3
public class xxx {
public void dummy_method(){
result.whenAnyExchangeReceived(new Processor() {
public void process(Exchange exchange) throws Exception {
String msg = exchange.getIn().getBody(String.class);
exchange.setException(new ThrottlingException(msg));
}
});
sendMessage("Message One");
sendMessage("Message Two");
final ServiceSupport consumer = (ServiceSupport) context.getRoute("foo").getConsumer();
await().atMost(2, TimeUnit.SECONDS).until(consumer::isSuspended);


log.info("sending message three");
}

};