//,temp,sample_3704.java,2,14,temp,sample_5067.java,2,17
//,3
public class xxx {
protected Message run() throws Exception {
try {
processor.process(exchange);
} catch (Throwable e) {
exchange.setException(e);
}
if (exchange.getException() != null) {
throw exchange.getException();
}


log.info("running fallback processor with exchange done");
}

};