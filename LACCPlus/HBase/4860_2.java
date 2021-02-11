//,temp,FlushSnapshotSubprocedure.java,200,209,temp,FlushTableSubprocedure.java,125,134
//,3
public class xxx {
  @Override
  public void cleanup(Exception e) {
    LOG.info("Aborting all flush region subprocedure task threads for '"
        + table + "' due to error", e);
    try {
      taskManager.cancelTasks();
    } catch (InterruptedException e1) {
      Thread.currentThread().interrupt();
    }
  }

};