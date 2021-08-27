//,temp,sample_6627.java,2,17,temp,sample_1790.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (uow != null) {
async = uow.beforeProcess(processor, exchange, callback);
}
if (LOG.isTraceEnabled()) {
}
boolean sync = processor.process(exchange, async);
if (uow != null) {
uow.afterProcess(processor, exchange, callback, sync);
}
if (LOG.isTraceEnabled()) {


log.info("exchange processed and is continued routed for exchangeid synchronously asynchronously");
}
}

};