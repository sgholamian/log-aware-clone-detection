//,temp,ReliabilityTest.java,170,178,temp,ReliabilityTest.java,151,158
//,3
public class xxx {
  private void runRandomWriterTest(final JobClient jc, 
      final Configuration conf, final String inputPath) 
  throws Exception {
    runTest(jc, conf, "org.apache.hadoop.examples.RandomWriter", 
        new String[]{inputPath}, 
        null, new KillTrackerThread(jc, 0, 0.4f, false, 1));
    LOG.info("RandomWriter job done");
  }

};