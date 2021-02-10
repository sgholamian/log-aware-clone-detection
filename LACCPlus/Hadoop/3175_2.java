//,temp,TestFixedLengthInputFormat.java,136,167,temp,TestFixedLengthInputFormat.java,102,131
//,3
public class xxx {
  @Test (timeout=5000)
  public void testNoRecordLength() throws Exception {
    localFs.delete(workDir, true);
    Path file = new Path(workDir, new String("testFormat.txt"));
    createFile(file, null, 10, 10);
    // Create the job and do not set fixed record length
    Job job = Job.getInstance(defaultConf);
    FileInputFormat.setInputPaths(job, workDir);
    FixedLengthInputFormat format = new FixedLengthInputFormat();
    List<InputSplit> splits = format.getSplits(job);
    boolean exceptionThrown = false;
    for (InputSplit split : splits) {
      try {
        TaskAttemptContext context = MapReduceTestUtil.
            createDummyMapTaskAttemptContext(job.getConfiguration());
        RecordReader<LongWritable, BytesWritable> reader =
            format.createRecordReader(split, context);
        MapContext<LongWritable, BytesWritable, LongWritable, BytesWritable>
            mcontext =
            new MapContextImpl<LongWritable, BytesWritable, LongWritable,
            BytesWritable>(job.getConfiguration(), context.getTaskAttemptID(),
            reader, null, null, MapReduceTestUtil.createDummyReporter(), split);
        reader.initialize(split, mcontext);
      } catch(IOException ioe) {
        exceptionThrown = true;
        LOG.info("Exception message:" + ioe.getMessage());
      }
    }
    assertTrue("Exception for not setting record length:", exceptionThrown);
  }

};