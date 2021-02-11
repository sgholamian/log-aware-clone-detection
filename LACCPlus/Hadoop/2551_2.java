//,temp,LocalJobRunner.java,738,741,temp,LocalJobRunner.java,733,736
//,3
public class xxx {
    @Override
    public void shuffleError(TaskAttemptID taskId, String message) throws IOException {
      LOG.error("shuffleError: "+ message + "from task: " + taskId);
    }

};