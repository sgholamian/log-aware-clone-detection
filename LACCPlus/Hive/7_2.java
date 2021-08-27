//,temp,TaskCommitContextRegistry.java,136,145,temp,TaskCommitContextRegistry.java,122,129
//,3
public class xxx {
  public synchronized void register(TaskAttemptContext context, TaskCommitterProxy committer) throws IOException {
    String key = generateKey(context);
    LOG.info("Registering committer for TaskAttemptID:" + key);
    if (taskCommitters.containsKey(key)) {
      LOG.warn("Replacing previous committer:" + committer);
    }
    taskCommitters.put(key, committer);
  }

};