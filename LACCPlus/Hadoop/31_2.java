//,temp,TestDFSHAAdminMiniCluster.java,252,259,temp,TestDFSHAAdmin.java,406,415
//,3
public class xxx {
  private Object runTool(String ... args) throws Exception {
    errOutBytes.reset();
    outBytes.reset();
    LOG.info("Running: DFSHAAdmin " + Joiner.on(" ").join(args));
    int ret = tool.run(args);
    errOutput = new String(errOutBytes.toByteArray(), Charsets.UTF_8);
    output = new String(outBytes.toByteArray(), Charsets.UTF_8);
    LOG.info("Err_output:\n" + errOutput + "\nOutput:\n" + output);
    return ret;
  }

};