//,temp,sample_936.java,2,16,temp,sample_935.java,2,15
//,3
public class xxx {
public void run() {
String threadName = Thread.currentThread().getName();
int currentRun = 0;
started = true;
try {
while (started && ++currentRun <= runCount) {
Thread.sleep(SLEEP_MILLIS);
}
} catch (InterruptedException e) {


log.info("runnable interrupted on run");
}
}

};