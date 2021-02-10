//,temp,ProcessTree.java,314,328,temp,ProcessTree.java,289,303
//,3
public class xxx {
  public static boolean isProcessGroupAlive(String pgrpId) {
    ShellCommandExecutor shexec = null;
    try {
      String[] args = { "kill", "-0", "-"+pgrpId };
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