//,temp,SysInfoWindows.java,81,92,temp,WindowsBasedProcessTree.java,102,113
//,3
public class xxx {
  String getSystemInfoInfoFromShell() {
    try {
      ShellCommandExecutor shellExecutor = new ShellCommandExecutor(
          new String[] {Shell.getWinUtilsFile().getCanonicalPath(),
              "systeminfo" });
      shellExecutor.execute();
      return shellExecutor.getOutput();
    } catch (IOException e) {
      LOG.error(StringUtils.stringifyException(e));
    }
    return null;
  }

};