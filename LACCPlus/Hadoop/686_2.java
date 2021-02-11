//,temp,ReliabilityTest.java,170,178,temp,ReliabilityTest.java,160,168
//,3
public class xxx {
  private void runSortTest(final JobClient jc, final Configuration conf,
      final String inputPath, final String outputPath) 
  throws Exception {
    runTest(jc, conf, "org.apache.hadoop.examples.Sort", 
        new String[]{inputPath, outputPath},
        new KillTaskThread(jc, 2, 0.2f, false, 2),
        new KillTrackerThread(jc, 2, 0.8f, false, 1));
    LOG.info("Sort job done");
  }

};