//,temp,sample_4515.java,2,17,temp,sample_4514.java,2,13
//,3
public class xxx {
private void prunePartitions() throws HiveException {
int expectedEvents = 0;
for (Map.Entry<String, List<SourceInfo>> entry : this.sourceInfoMap.entrySet()) {
String source = entry.getKey();
for (SourceInfo si : entry.getValue()) {
int taskNum = context.getVertexNumTasks(source);


log.info("expecting events for vertex for column");
}
}
}

};