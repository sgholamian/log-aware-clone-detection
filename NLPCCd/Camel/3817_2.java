//,temp,sample_1727.java,2,18,temp,sample_6519.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (LOG.isTraceEnabled()) {
}
Exchange exchange = getExchange(ctx);
AsyncCallback callback = getAsyncCallback(ctx);
producer.removeState(ctx.getChannel());
producer.getAllChannels().remove(ctx.getChannel());
boolean doneUoW = exchange.getUnitOfWork() == null;
if (producer.getConfiguration().isSync() && !doneUoW && !messageReceived && !exceptionHandled) {
exceptionHandled = true;
if (LOG.isDebugEnabled()) {


log.info("channel closed but no message received from address");
}
}
}

};