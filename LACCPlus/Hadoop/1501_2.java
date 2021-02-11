//,temp,TestDistCpViewFs.java,463,474,temp,TestIntegration.java,559,574
//,3
public class xxx {
  private void runTest(Path listFile, Path target, boolean targetExists, 
      boolean sync, boolean delete,
      boolean overwrite) throws IOException {
    DistCpOptions options = new DistCpOptions(listFile, target);
    options.setSyncFolder(sync);
    options.setDeleteMissing(delete);
    options.setOverwrite(overwrite);
    options.setTargetPathExists(targetExists);
    options.setNumListstatusThreads(numListstatusThreads);
    try {
      new DistCp(getConf(), options).execute();
    } catch (Exception e) {
      LOG.error("Exception encountered ", e);
      throw new IOException(e);
    }
  }

};