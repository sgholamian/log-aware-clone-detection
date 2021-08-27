//,temp,sample_4515.java,2,17,temp,sample_4514.java,2,13
//,3
public class xxx {
public void dummy_method(){
int expectedEvents = 0;
for (Map.Entry<String, List<SourceInfo>> entry : this.sourceInfoMap.entrySet()) {
String source = entry.getKey();
for (SourceInfo si : entry.getValue()) {
int taskNum = context.getVertexNumTasks(source);
expectedEvents += taskNum;
prunePartitionSingleSource(source, si);
}
}
if (expectedEvents != totalEventCount) {


log.info("expecting received");
}
}

};