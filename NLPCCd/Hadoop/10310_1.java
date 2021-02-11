//,temp,sample_5943.java,2,19,temp,sample_5787.java,2,16
//,3
public class xxx {
public void dummy_method(){
Iterator<JobHistoryEvent> it = eventQueue.iterator();
while(it.hasNext()) {
JobHistoryEvent ev = it.next();
handleEvent(ev);
}
if(forceJobCompletion) {
for (Map.Entry<JobId,MetaInfo> jobIt : fileMap.entrySet()) {
JobId toClose = jobIt.getKey();
MetaInfo mi = jobIt.getValue();
if(mi != null && mi.isWriterActive()) {


log.info("found jobid to have not been closed will close");
}
}
}
}

};