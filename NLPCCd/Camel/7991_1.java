//,temp,sample_7830.java,2,17,temp,sample_1103.java,2,18
//,3
public class xxx {
public void run() throws Throwable {
Throwable rce;
processByErrorHandler(exchange);
if (exchange.getException() != null || exchange.isRollbackOnly()) {
if (exchange.getException() != null) {
rce = exchange.getException();
} else {
rce = new TransactionRolledbackException();
}
if (log.isTraceEnabled()) {


log.info("throwing runtime exception to force transaction to rollback on");
}
}
}

};