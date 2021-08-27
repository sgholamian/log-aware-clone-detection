//,temp,sample_1933.java,2,18,temp,sample_1932.java,2,14
//,3
public class xxx {
public void process(WatchedEvent event) {
if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
if (znode != null) {
try {
znode.close();
} catch (IOException e) {
} finally {
HiveServer2.this.setDeregisteredWithZooKeeper(true);
if (cliService.getSessionManager().getOpenSessionCount() == 0) {


log.info("this instance of has been removed from the list of server instances available for dynamic service discovery the last client session has ended will shutdown now");
}
}
}
}
}

};