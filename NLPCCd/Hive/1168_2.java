//,temp,sample_2109.java,2,17,temp,sample_2099.java,2,15
//,3
public class xxx {
public void watch() {
while (!shutdown.get()) {
WatchKey watchKey;
try {
watchKey = watchService.take();
} catch (InterruptedException e) {
if (shutdown.get()) {


log.info("shutting down watcher");
}
}
}
}

};