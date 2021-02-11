//,temp,TestDistCpViewFs.java,414,427,temp,TestIntegration.java,559,576
//,3
public class xxx {
  private void runTest(Path listFile, Path target, boolean targetExists, 
      boolean sync, boolean delete,
      boolean overwrite) throws IOException {
    final DistCpOptions options = new DistCpOptions.Builder(listFile, target)
        .withSyncFolder(sync)
        .withDeleteMissing(delete)
        .withOverwrite(overwrite)
        .withNumListstatusThreads(numListstatusThreads)
        .build();
    try {
      final DistCp distCp = new DistCp(getConf(), options);
      distCp.context.setTargetPathExists(targetExists);
      distCp.execute();
    } catch (Exception e) {
      LOG.error("Exception encountered ", e);
      throw new IOException(e);
    }
  }

};