//,temp,TestDFSHAAdminMiniCluster.java,255,262,temp,TestDFSHAAdmin.java,417,426
//,3
public class xxx {
  private int runTool(String ... args) throws Exception {
    errOutBytes.reset();
    LOG.info("Running: DFSHAAdmin " + Joiner.on(" ").join(args));
    int ret = tool.run(args);
    errOutput = new String(errOutBytes.toByteArray(), Charsets.UTF_8);
    LOG.info("Output:\n" + errOutput);
    return ret;
  }

};