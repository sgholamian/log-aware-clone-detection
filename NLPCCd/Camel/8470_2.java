//,temp,sample_6511.java,2,16,temp,sample_1687.java,2,16
//,2
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