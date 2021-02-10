//,temp,TestShellBasedUnixGroupsMapping.java,151,162,temp,TestShellBasedUnixGroupsMapping.java,136,149
//,2
public class xxx {
    @Override
    protected ShellCommandExecutor createGroupIDExecutor(String userName) {
      ShellCommandExecutor executor = mock(ShellCommandExecutor.class);

      try {
        doNothing().when(executor).execute();
        when(executor.getOutput()).thenReturn("111\n111 112 113");
      } catch (IOException e) {
        TESTLOG.warn(e.getMessage());
      }
      return executor;
    }

};