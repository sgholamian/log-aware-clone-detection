//,temp,sample_2108.java,2,15,temp,sample_2100.java,2,17
//,3
public class xxx {
public Void call() {
while (!shutdown.get()) {
WatchedPathInfo pathInfo;
try {
pathInfo = watchedPathQueue.take();
} catch (InterruptedException e) {
if (shutdown.get()) {


log.info("shutting down watchexpirer");
}
}
}
}

};