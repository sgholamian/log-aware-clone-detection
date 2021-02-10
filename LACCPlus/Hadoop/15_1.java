//,temp,SysInfoWindows.java,72,82,temp,WindowsBasedProcessTree.java,77,87
//,3
public class xxx {
  String getSystemInfoInfoFromShell() {
    ShellCommandExecutor shellExecutor = new ShellCommandExecutor(
        new String[] {Shell.WINUTILS, "systeminfo" });
    try {
      shellExecutor.execute();
      return shellExecutor.getOutput();
    } catch (IOException e) {
      LOG.error(StringUtils.stringifyException(e));
    }
    return null;
  }

};