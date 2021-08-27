//,temp,sample_1933.java,2,18,temp,sample_1932.java,2,14
//,3
public class xxx {
public void process(WatchedEvent event) {
if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
if (znode != null) {
try {
znode.close();
} catch (IOException e) {


log.info("failed to close the persistent ephemeral znode");
}
}
}
}

};