//,temp,sample_2830.java,2,14,temp,sample_8289.java,2,14
//,3
public class xxx {
public void stop() {
if (!isStopping() && !isStopped()) {
try {
super.stop();
} catch (Exception e) {
throw wrapRuntimeCamelException(e);
}
} else {


log.info("ignoring stop as camel is already stopped");
}
}

};