//,temp,FsDatasetAsyncDiskService.java,236,242,temp,TestFsShellTouch.java,58,62
//,3
public class xxx {
  private int shellRun(String... args) throws Exception {
    int exitCode = shell.run(args);
    LOG.info("exit " + exitCode + " - " + StringUtils.join(" ", args));
    return exitCode;
  }

};