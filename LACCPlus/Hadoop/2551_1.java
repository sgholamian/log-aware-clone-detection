//,temp,LocalJobRunner.java,738,741,temp,LocalJobRunner.java,733,736
//,3
public class xxx {
    public synchronized void fatalError(TaskAttemptID taskId, String msg, boolean fastFail)
    throws IOException {
      LOG.error("Fatal: "+ msg + " from task: " + taskId + " fast fail: " + fastFail);
    }

};