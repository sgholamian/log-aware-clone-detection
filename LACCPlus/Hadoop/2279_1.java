//,temp,TestDistCpViewFs.java,414,427,temp,TestIntegration.java,559,576
//,3
public class xxx {
  private void runTest(Path listFile, Path target, boolean targetExists, 
      boolean sync) throws IOException {
    final DistCpOptions options = new DistCpOptions.Builder(listFile, target)
        .withSyncFolder(sync)
        .build();
    try {
      final DistCp distcp = new DistCp(getConf(), options);
      distcp.context.setTargetPathExists(targetExists);
      distcp.execute();
    } catch (Exception e) {
      LOG.error("Exception encountered ", e);
      throw new IOException(e);
    }
  }

};