//,temp,sample_7597.java,2,13,temp,sample_7599.java,2,14
//,2
public class xxx {
public void run() {
try {
int scanned = scanActiveLogs();
} catch (Exception e) {
Throwable t = extract(e);
if (t instanceof InterruptedException) {


log.info("file scanner interrupted");
}
}
}

};