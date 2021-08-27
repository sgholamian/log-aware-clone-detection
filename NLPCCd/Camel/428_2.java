//,temp,sample_1194.java,2,8,temp,sample_7588.java,2,11
//,3
public class xxx {
public boolean process(Exchange exchange, AsyncCallback callback) {
if (shutdown.get()) {
throw new IllegalStateException("ThreadsProcessor is not running.");
}
if (exchange.isTransacted()) {


log.info("transacted exchange must be routed synchronously for exchangeid");
}
}

};