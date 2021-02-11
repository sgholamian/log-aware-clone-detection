//,temp,UtilTest.java,129,145,temp,WindowsBasedProcessTree.java,77,87
//,3
public class xxx {
  public static boolean hasPerlSupport() {
    boolean hasPerl = false;
    ShellCommandExecutor shexec = new ShellCommandExecutor(
      new String[] { "perl", "-e", "print 42" });
    try {
      shexec.execute();
      if (shexec.getOutput().equals("42")) {
        hasPerl = true;
      }
      else {
        LOG.warn("Perl is installed, but isn't behaving as expected.");
      }
    } catch (Exception e) {
      LOG.warn("Could not run perl: " + e);
    }
    return hasPerl;
  }

};