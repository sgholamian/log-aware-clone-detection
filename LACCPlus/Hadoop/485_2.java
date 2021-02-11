//,temp,TestCombineTextInputFormat.java,75,141,temp,TestCombineSequenceFileInputFormat.java,67,130
//,3
public class xxx {
  @Test(timeout=10000)
  public void testFormat() throws IOException, InterruptedException {
    Job job = Job.getInstance(conf);

    Random random = new Random();
    long seed = random.nextLong();
    random.setSeed(seed);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(job, workDir);

    final int length = 10000;
    final int numFiles = 10;

    // create files with a variety of lengths
    createFiles(length, numFiles, random, job);

    TaskAttemptContext context = MapReduceTestUtil.
      createDummyMapTaskAttemptContext(job.getConfiguration());
    // create a combine split for the files
    InputFormat<IntWritable,BytesWritable> format =
      new CombineSequenceFileInputFormat<IntWritable,BytesWritable>();
    for (int i = 0; i < 3; i++) {
      int numSplits =
        random.nextInt(length/(SequenceFile.SYNC_INTERVAL/20)) + 1;
      LOG.info("splitting: requesting = " + numSplits);
      List<InputSplit> splits = format.getSplits(job);
      LOG.info("splitting: got =        " + splits.size());

      // we should have a single split as the length is comfortably smaller than
      // the block size
      assertEquals("We got more than one splits!", 1, splits.size());
      InputSplit split = splits.get(0);
      assertEquals("It should be CombineFileSplit",
        CombineFileSplit.class, split.getClass());

      // check the split
      BitSet bits = new BitSet(length);
      RecordReader<IntWritable,BytesWritable> reader =
        format.createRecordReader(split, context);
      MapContext<IntWritable,BytesWritable,IntWritable,BytesWritable> mcontext =
        new MapContextImpl<IntWritable,BytesWritable,IntWritable,BytesWritable>(job.getConfiguration(),
        context.getTaskAttemptID(), reader, null, null,
        MapReduceTestUtil.createDummyReporter(), split);
      reader.initialize(split, mcontext);
      assertEquals("reader class is CombineFileRecordReader.",
        CombineFileRecordReader.class, reader.getClass());

      try {
        while (reader.nextKeyValue()) {
          IntWritable key = reader.getCurrentKey();
          BytesWritable value = reader.getCurrentValue();
          assertNotNull("Value should not be null.", value);
          final int k = key.get();
          LOG.debug("read " + k);
          assertFalse("Key in multiple partitions.", bits.get(k));
          bits.set(k);
        }
      } finally {
        reader.close();
      }
      assertEquals("Some keys in no partition.", length, bits.cardinality());
    }
  }

};