//,temp,sample_163.java,2,11,temp,sample_164.java,2,11
//,2
public class xxx {
public boolean process(Exchange exchange, AsyncCallback callback) {
try {
throw new IllegalArgumentException("Forced to dump stacktrace");
} catch (Exception e) {
e.fillInStackTrace();


log.info("there are lines in the stacktrace");
}
}

};