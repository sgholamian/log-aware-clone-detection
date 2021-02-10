//,temp,DockerContainerDeletionTask.java,53,62,temp,DeletionService.java,85,95
//,3
public class xxx {
  @Override
  public void run() {
    if (LOG.isDebugEnabled()) {
      String msg = String.format("Running DeletionTask : %s", toString());
      LOG.debug(msg);
    }
    LinuxContainerExecutor exec = ((LinuxContainerExecutor)
        getDeletionService().getContainerExecutor());
    exec.removeDockerContainer(containerId);
  }

};