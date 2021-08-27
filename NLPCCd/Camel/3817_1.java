//,temp,sample_1727.java,2,18,temp,sample_6519.java,2,18
//,3
public class xxx {
public void dummy_method(){
Exchange exchange = getExchange(ctx);
AsyncCallback callback = getAsyncCallback(ctx);
producer.removeState(ctx.channel());
producer.getAllChannels().remove(ctx.channel());
boolean doneUoW = exchange.getUnitOfWork() == null;
NettyConfiguration configuration = producer.getConfiguration();
if (configuration.isSync() && !doneUoW && !messageReceived && !exceptionHandled) {
exceptionHandled = true;
String address = configuration != null ? configuration.getAddress() : "";
if (LOG.isDebugEnabled()) {


log.info("channel closed but no message received from address");
}
}
}

};