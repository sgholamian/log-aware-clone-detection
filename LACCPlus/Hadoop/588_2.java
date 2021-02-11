//,temp,ProcessTree.java,53,67,temp,TestProcfsBasedProcessTree.java,883,897
//,2
public class xxx {
  protected static boolean isSetsidAvailable() {
    ShellCommandExecutor shexec = null;
    boolean setsidSupported = true;
    try {
      String[] args = { "setsid", "bash", "-c", "echo $$" };
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