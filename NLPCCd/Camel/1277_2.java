//,temp,sample_6343.java,2,16,temp,sample_2487.java,2,15
//,3
public class xxx {
public Object call() throws Exception {
Thread.sleep(getEndpoint().getDelay());
int count = counter.incrementAndGet();
if (getEndpoint().getFailFirstAttempts() >= count) {
exchange.setException(new CamelExchangeException("Simulated error at attempt " + count, exchange));
} else {
String reply = getEndpoint().getReply();
exchange.getOut().setBody(reply);
exchange.getOut().setHeaders(exchange.getIn().getHeaders());
}


log.info("callback done false");
}

};