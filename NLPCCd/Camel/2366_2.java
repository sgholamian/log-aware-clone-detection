//,temp,sample_5151.java,2,12,temp,sample_3570.java,2,12
//,2
public class xxx {
private void notifyElectionWatchers() {
for (ElectionWatcher watcher : watchers) {
try {
watcher.electionResultChanged();
} catch (Exception e) {


log.info("election watcher of type threw an exception");
}
}
}

};