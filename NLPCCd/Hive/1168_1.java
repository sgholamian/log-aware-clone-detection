//,temp,sample_2109.java,2,17,temp,sample_2099.java,2,15
//,3
public class xxx {
public Void call() {
while (!shutdown.get()) {
WatchedPathInfo pathInfo;
try {
pathInfo = watchedPathQueue.take();
} catch (InterruptedException e) {
if (shutdown.get()) {
break;
} else {


log.info("watchexpirer interrupted before being shutdown");
}
}
}
}

};