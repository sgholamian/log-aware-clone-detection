//,temp,sample_749.java,9,17,temp,sample_1025.java,2,9
//,3
public class xxx {
public void commitTask(TaskAttemptContext context) throws IOException {
for (Map.Entry<String, OutputJobInfo> outputJobInfoEntry : dynamicOutputJobInfo.entrySet()) {
String dynKey = outputJobInfoEntry.getKey();
OutputJobInfo outputJobInfo = outputJobInfoEntry.getValue();


log.info("committing task attempt for");
}
}

};