//,temp,ContainerExecutor.java,801,815,temp,JobHistoryEventHandler.java,588,599
//,3
public class xxx {
  public String getProcessId(ContainerId containerID) {
    String pid = null;
    Path pidFile = pidFiles.get(containerID);

    // If PID is null, this container hasn't launched yet.
    if (pidFile != null) {
      try {
        pid = ProcessIdFileReader.getProcessId(pidFile);
      } catch (IOException e) {
        LOG.error("Got exception reading pid from pid-file " + pidFile, e);
      }
    }

    return pid;
  }

};