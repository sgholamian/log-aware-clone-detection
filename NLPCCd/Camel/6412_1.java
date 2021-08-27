//,temp,sample_1854.java,2,19,temp,sample_1855.java,2,19
//,3
public class xxx {
public void dummy_method(){
String address = getEndpoint().getAddress();
boolean reply = ExchangeHelper.isOutCapable(exchange);
boolean pubSub = getEndpoint().isPubSub();
Object body = getVertxBody(exchange);
if (body != null) {
if (reply) {
eventBus.send(address, body, new CamelReplyHandler(exchange, callback));
return false;
} else {
if (pubSub) {


log.info("publishing to with body");
}
}
}
}

};