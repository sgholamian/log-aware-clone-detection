//,temp,ReliabilityTest.java,170,178,temp,ReliabilityTest.java,151,158
//,3
public class xxx {
  private void runSortValidatorTest(final JobClient jc, 
      final Configuration conf, final String inputPath, final String outputPath)
  throws Exception {
    runTest(jc, conf, "org.apache.hadoop.mapred.SortValidator", new String[] {
        "-sortInput", inputPath, "-sortOutput", outputPath},
        new KillTaskThread(jc, 2, 0.2f, false, 1),
        new KillTrackerThread(jc, 2, 0.8f, false, 1));  
    LOG.info("SortValidator job done");    
  }

};