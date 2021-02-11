//,temp,TestShellBasedUnixGroupsMapping.java,151,162,temp,TestShellBasedUnixGroupsMapping.java,136,149
//,2
public class xxx {
    @Override
    protected ShellCommandExecutor createGroupExecutor(String userName) {
      ShellCommandExecutor executor = mock(ShellCommandExecutor.class);

      try {
        // There is a numerical group 23, but no group name 23.
        // Thus 23 is treated as a resolvable group name.
        doNothing().when(executor).execute();
        when(executor.getOutput()).thenReturn("23\n23 groupname zzz");
      } catch (IOException e) {
        TESTLOG.warn(e.getMessage());
      }
      return executor;
    }

};