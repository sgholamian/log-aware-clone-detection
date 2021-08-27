//,temp,sample_1854.java,2,19,temp,sample_1855.java,2,19
//,3
public class xxx {
public void dummy_method(){
boolean pubSub = getEndpoint().isPubSub();
Object body = getVertxBody(exchange);
if (body != null) {
if (reply) {
eventBus.send(address, body, new CamelReplyHandler(exchange, callback));
return false;
} else {
if (pubSub) {
eventBus.publish(address, body);
} else {


log.info("sending to with body");
}
}
}
}

};