//,temp,sample_1692.java,2,12,temp,sample_585.java,2,12
//,3
public class xxx {
public String turnOff() {
if (s_executor != null) {
try {
s_executor.shutdown();
} catch (Throwable th) {


log.info("unable to shutdown the executor");
}
}
}

};