//,temp,sample_3178.java,2,12,temp,sample_3177.java,2,11
//,3
public class xxx {
private void processWatchedEvent(WatchedEvent event) {
if (event.getType() != EventType.NodeDeleted) return;
String localPath = nodePath.get();
if (localPath == null) return;
if (!localPath.equals(event.getPath())) {
return;
}


log.info("trying to reacquire because of the nodedeleted event");
}

};