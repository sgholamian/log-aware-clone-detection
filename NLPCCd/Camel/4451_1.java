//,temp,sample_1985.java,2,15,temp,sample_1984.java,2,15
//,2
public class xxx {
protected void onExceptionOccurred(Exchange exchange, final RedeliveryData data) {
if (data.onExceptionProcessor == null) {
return;
}
try {
if (log.isTraceEnabled()) {
}
data.onExceptionProcessor.process(exchange);
} catch (Throwable e) {
}


log.info("onexceptionoccurred processor done");
}

};