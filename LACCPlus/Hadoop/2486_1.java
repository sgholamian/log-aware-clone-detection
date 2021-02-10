//,temp,TestCombineTextInputFormat.java,75,141,temp,TestMRKeyValueTextInputFormat.java,68,154
//,3
public class xxx {
  @Test(timeout=10000)
  public void testFormat() throws Exception {
    Job job = Job.getInstance(new Configuration(defaultConf));

    Random random = new Random();
    long seed = random.nextLong();
    LOG.info("seed = " + seed);
    random.setSeed(seed);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(job, workDir);

    final int length = 10000;
    final int numFiles = 10;

    // create files with various lengths
    createFiles(length, numFiles, random);

    // create a combined split for the files
    CombineTextInputFormat format = new CombineTextInputFormat();
    for (int i = 0; i < 3; i++) {
      int numSplits = random.nextInt(length/20) + 1;
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
      LOG.debug("split= " + split);
      TaskAttemptContext context = MapReduceTestUtil.
        createDummyMapTaskAttemptContext(job.getConfiguration());
      RecordReader<LongWritable, Text> reader =
        format.createRecordReader(split, context);
      assertEquals("reader class is CombineFileRecordReader.",
        CombineFileRecordReader.class, reader.getClass());
      MapContext<LongWritable,Text,LongWritable,Text> mcontext =
        new MapContextImpl<LongWritable,Text,LongWritable,Text>(job.getConfiguration(),
        context.getTaskAttemptID(), reader, null, null,
        MapReduceTestUtil.createDummyReporter(), split);
      reader.initialize(split, mcontext);

      try {
        int count = 0;
        while (reader.nextKeyValue()) {
          LongWritable key = reader.getCurrentKey();
          assertNotNull("Key should not be null.", key);
          Text value = reader.getCurrentValue();
          final int v = Integer.parseInt(value.toString());
          LOG.debug("read " + v);
          assertFalse("Key in multiple partitions.", bits.get(v));
          bits.set(v);
          count++;
        }
        LOG.debug("split=" + split + " count=" + count);
      } finally {
        reader.close();
      }
      assertEquals("Some keys in no partition.", length, bits.cardinality());
    }
  }

};