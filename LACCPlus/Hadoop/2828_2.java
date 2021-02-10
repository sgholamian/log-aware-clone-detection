//,temp,ProcessTree.java,124,141,temp,ProcessTree.java,52,66
//,3
public class xxx {
  private static boolean isSetsidSupported() {
    ShellCommandExecutor shexec = null;
    boolean setsidSupported = true;
    try {
      String[] args = {"setsid", "bash", "-c", "echo $$"};
      shexec = new ShellCommandExecutor(args);
      shexec.execute();
    } catch (IOException ioe) {
      LOG.warn("setsid is not available on this machine. So not using it.");
      setsidSupported = false;
    } finally { // handle the exit code
      LOG.info("setsid exited with exit code " + shexec.getExitCode());
    }
    return setsidSupported;
  }

};