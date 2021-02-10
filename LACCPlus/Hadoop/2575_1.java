//,temp,TestShellBasedUnixGroupsMapping.java,215,226,temp,TestShellBasedUnixGroupsMapping.java,91,106
//,3
public class xxx {
    @Override
    protected ShellCommandExecutor createGroupExecutor(String userName) {
      ShellCommandExecutor executor = mock(ShellCommandExecutor.class);

      try {
        doNothing().when(executor).execute();
        when(executor.getOutput()).thenReturn("abc\ndef abc hij");
      } catch (IOException e) {
        TESTLOG.warn(e.getMessage());
      }
      return executor;
    }

};