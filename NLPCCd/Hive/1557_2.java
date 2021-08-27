//,temp,sample_1026.java,2,10,temp,sample_1024.java,2,10
//,3
public class xxx {
public MultiRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
baseRecordWriters = new LinkedHashMap<String, BaseRecordWriterContainer>();
String[] aliases = getOutputFormatAliases(context);
for (String alias : aliases) {


log.info("creating record writer for alias");
}
}

};