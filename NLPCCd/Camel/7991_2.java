//,temp,sample_7830.java,2,17,temp,sample_1103.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (exchange.getException() != null || exchange.isRollbackOnly()) {
if (exchange.getException() != null) {
rce = ObjectHelper.wrapRuntimeCamelException(exchange.getException());
} else {
rce = new TransactionRollbackException();
}
if (!status.isRollbackOnly()) {
status.setRollbackOnly();
}
if (log.isTraceEnabled()) {


log.info("throwing runtime exception to force transaction to rollback on");
}
}
}

};