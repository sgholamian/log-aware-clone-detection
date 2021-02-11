//,temp,sample_3673.java,2,16,temp,sample_3674.java,2,17
//,3
public class xxx {
public void run() {
try {
while (shouldRun) {
try {
blockTokenSecretManager.addKeys(namenode.getBlockKeys());
} catch (IOException e) {
}
Thread.sleep(sleepInterval);
}
} catch (InterruptedException e) {


log.info("interruptedexception in block key updater thread");
}
}

};