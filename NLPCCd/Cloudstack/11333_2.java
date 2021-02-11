//,temp,sample_1692.java,2,12,temp,sample_585.java,2,12
//,3
public class xxx {
public String resetKeepAliveTask(int seconds) {
if (_executor != null) {
try {
_executor.shutdown();
} catch (Exception e) {


log.info("unable to shutdown executor");
}
}
}

};