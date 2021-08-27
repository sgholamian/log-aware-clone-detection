//,temp,sample_1026.java,2,10,temp,sample_1024.java,2,10
//,3
public class xxx {
public MultiOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
outputCommitters = new LinkedHashMap<String, MultiOutputFormat.BaseOutputCommitterContainer>();
String[] aliases = getOutputFormatAliases(context);
for (String alias : aliases) {


log.info("creating output committer for alias");
}
}

};