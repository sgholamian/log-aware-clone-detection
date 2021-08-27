//,temp,sample_2108.java,2,15,temp,sample_2100.java,2,17
//,3
public class xxx {
public void watch() {
while (!shutdown.get()) {
WatchKey watchKey;
try {
watchKey = watchService.take();
} catch (InterruptedException e) {
if (shutdown.get()) {
break;
} else {


log.info("watcher interrupted before being shutdown");
}
}
}
}

};