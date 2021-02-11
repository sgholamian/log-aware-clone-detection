//,temp,TestFixedLengthInputFormat.java,136,167,temp,TestFixedLengthInputFormat.java,102,131
//,3
public class xxx {
  @Test (timeout=5000)
  public void testZeroRecordLength() throws Exception {
    localFs.delete(workDir, true);
    Path file = new Path(workDir, new String("testFormat.txt"));
    createFile(file, null, 10, 10);
    Job job = Job.getInstance(defaultConf);
    // Set the fixed length record length config property 
    FixedLengthInputFormat format = new FixedLengthInputFormat();
    format.setRecordLength(job.getConfiguration(), 0);
    FileInputFormat.setInputPaths(job, workDir);
    List<InputSplit> splits = format.getSplits(job);
    boolean exceptionThrown = false;
    for (InputSplit split : splits) {
      try {
        TaskAttemptContext context =
            MapReduceTestUtil.createDummyMapTaskAttemptContext(
            job.getConfiguration());
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
    assertTrue("Exception for zero record length:", exceptionThrown);
  }

};