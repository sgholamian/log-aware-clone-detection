//,temp,TestFixedLengthInputFormat.java,435,464,temp,TestFixedLengthInputFormat.java,172,202
//,3
public class xxx {
  private void runPartialRecordTest(CompressionCodec codec) throws Exception {
    localFs.delete(workDir, true);
    Job job = Job.getInstance(defaultConf);
    // Create a file with fixed length records with 5 byte long
    // records with a partial record at the end.
    StringBuilder fileName = new StringBuilder("testFormat.txt");
    if (codec != null) {
      fileName.append(".gz");
      ReflectionUtils.setConf(codec, job.getConfiguration());
    }
    writeFile(localFs, new Path(workDir, fileName.toString()), codec,
        "one  two  threefour five six  seveneightnine ten");
    FixedLengthInputFormat format = new FixedLengthInputFormat();
    format.setRecordLength(job.getConfiguration(), 5);
    FileInputFormat.setInputPaths(job, workDir);
    List<InputSplit> splits = format.getSplits(job);
    if (codec != null) {
      assertEquals("compressed splits == 1", 1, splits.size());
    }
    boolean exceptionThrown = false;
    for (InputSplit split : splits) {
      try {
        List<String> results = readSplit(format, split, job);
      } catch(IOException ioe) {
        exceptionThrown = true;
        LOG.info("Exception message:" + ioe.getMessage());
      }
    }
    assertTrue("Exception for partial record:", exceptionThrown);
  }

};