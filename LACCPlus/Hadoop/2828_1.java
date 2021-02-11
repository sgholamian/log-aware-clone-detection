//,temp,ProcessTree.java,124,141,temp,ProcessTree.java,52,66
//,3
public class xxx {
  private static void sendSignal(String pid, int signalNum, String signalName) {
    ShellCommandExecutor shexec = null;
    try {
      String[] args = { "kill", "-" + signalNum, pid };
      shexec = new ShellCommandExecutor(args);
      shexec.execute();
    } catch (IOException ioe) {
      LOG.warn("Error executing shell command " + ioe);
    } finally {
      if (pid.startsWith("-")) {
        LOG.info("Sending signal to all members of process group " + pid
            + ": " + signalName + ". Exit code " + shexec.getExitCode());
      } else {
        LOG.info("Signaling process " + pid
            + " with " + signalName + ". Exit code " + shexec.getExitCode());
      }
    }
  }

};