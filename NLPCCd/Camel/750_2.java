//,temp,sample_876.java,2,11,temp,sample_875.java,2,10
//,3
public class xxx {
private void doPublish(Exchange exchange, AsyncCallback callback) {
Object body = exchange.getIn().getBody();
if (ObjectHelper.isEmpty(body)) {
throw new RuntimeException("Can not publish empty message");
}


log.info("sending message to channel");
}

};