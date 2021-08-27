//,temp,RemoteMetastoreTaskThreadTestImpl2.java,50,59,temp,RemoteMetastoreTaskThreadTestImpl1.java,50,59
//,2
public class xxx {
  @Override
  public void run() {
    LOG.info("Name of thread " + Thread.currentThread().getName() + " changed to " + TASK_NAME);
    Thread.currentThread().setName(TASK_NAME);
    try {
      Thread.sleep(10000);
    } catch (InterruptedException ie) {
      LOG.error("Task " + TASK_NAME + " interrupted: " + ie.getMessage(), ie);
    }
  }

};