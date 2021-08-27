//,temp,sample_876.java,2,11,temp,sample_875.java,2,10
//,3
public class xxx {
private void doFire(Exchange exchange, AsyncCallback callback) {
Object body = exchange.getIn().getBody();
if (ObjectHelper.isEmpty(body)) {
exchange.setException(new CamelException("Can not fire empty message"));
callback.done(true);
}


log.info("sending message to channel");
}

};