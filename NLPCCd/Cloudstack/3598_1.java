//,temp,sample_4673.java,2,13,temp,sample_407.java,2,13
//,2
public class xxx {
private boolean shouldRetry(final int numRetries) {
try {
if (numRetries > 0) {
login();
return true;
}
} catch (final Exception e) {


log.info("failed to log in to netscaler device at due to");
}
}

};