//,temp,ProcessTree.java,286,300,temp,TestProcfsBasedProcessTree.java,821,835
//,3
public class xxx {
  public static boolean isAlive(String pid) {
    ShellCommandExecutor shexec = null;
    try {
      String[] args = { "kill", "-0", pid };
      shexec = new ShellCommandExecutor(args);
      shexec.execute();
    } catch (ExitCodeException ee) {
      return false;
    } catch (IOException ioe) {
      LOG.warn("Error executing shell command "
          + shexec.toString() + ioe);
      return false;
    }
    return (shexec.getExitCode() == 0 ? true : false);
  }

};