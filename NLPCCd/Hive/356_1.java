//,temp,sample_2974.java,2,9,temp,sample_2971.java,2,9
//,3
public class xxx {
public synchronized void register(TaskAttemptContext context, TaskCommitterProxy committer) throws IOException {
String key = generateKey(context);
if (taskCommitters.containsKey(key)) {


log.info("replacing previous committer");
}
}

};