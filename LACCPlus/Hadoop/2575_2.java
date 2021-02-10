//,temp,TestShellBasedUnixGroupsMapping.java,215,226,temp,TestShellBasedUnixGroupsMapping.java,91,106
//,3
public class xxx {
    @Override
    protected ShellCommandExecutor createGroupExecutor(String userName) {
      ShellCommandExecutor executor = mock(ShellCommandExecutor.class);

      try {
        // There is both a group name 9999 and a group ID 9999.
        // This is treated as unresolvable group.
        doThrow(new ExitCodeException(1, "cannot find name for group ID 9999")).
            when(executor).execute();

        when(executor.getOutput()).thenReturn("9999\n9999 abc def");
      } catch (IOException e) {
        TESTLOG.warn(e.getMessage());
      }
      return executor;
    }

};