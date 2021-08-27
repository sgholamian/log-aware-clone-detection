//,temp,TaskCommitContextRegistry.java,136,145,temp,TaskCommitContextRegistry.java,122,129
//,3
public class xxx {
  public synchronized void discardCleanupFor(TaskAttemptContext context) throws IOException {
    String key = generateKey(context);
    LOG.info("Discarding all cleanup for TaskAttemptID:" + key);
    if (!taskCommitters.containsKey(key)) {
      LOG.warn("No committer registered for TaskAttemptID:" + key);
    }
    else {
      taskCommitters.remove(key);
    }
  }

};