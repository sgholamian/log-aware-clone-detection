//,temp,WindowsSecureContainerExecutor.java,602,617,temp,WindowsSecureContainerExecutor.java,573,584
//,3
public class xxx {
  @Override
  protected String[] getRunCommand(String command, String groupId,
      String userName, Path pidFile, Configuration conf) {
    File f = new File(command);
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("getRunCommand: %s exists:%b", 
          command, f.exists()));
    }
    return new String[] { Shell.getWinUtilsPath(), "task",
        "createAsUser", groupId,
        userName, pidFile.toString(), "cmd /c " + command };
  }

};