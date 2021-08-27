//,temp,sample_5514.java,2,15,temp,sample_6344.java,2,16
//,3
public class xxx {
public void dummy_method(){
Thread.sleep(getEndpoint().getDelay());
int count = counter.incrementAndGet();
if (getEndpoint().getFailFirstAttempts() >= count) {
exchange.setException(new CamelExchangeException("Simulated error at attempt " + count, exchange));
} else {
String reply = getEndpoint().getReply();
reply = getEndpoint().isAppend() ? exchange.getIn().getBody() + " " + reply : reply;
exchange.getOut().setBody(reply);
exchange.getOut().setHeaders(exchange.getIn().getHeaders());
}


log.info("callback done false");
}

};