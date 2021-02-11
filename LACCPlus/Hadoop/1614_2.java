//,temp,TestCombineTextInputFormat.java,75,141,temp,TestMRKeyValueTextInputFormat.java,70,156
//,3
public class xxx {
  @Test
  public void testFormat() throws Exception {
    Job job = Job.getInstance(new Configuration(defaultConf));
    Path file = new Path(workDir, "test.txt");

    int seed = new Random().nextInt();
    LOG.info("seed = " + seed);
    Random random = new Random(seed);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(job, workDir);

    final int MAX_LENGTH = 10000;
    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length += random.nextInt(MAX_LENGTH / 10) + 1) {

      LOG.debug("creating; entries = " + length);

      // create a file with length entries
      Writer writer = new OutputStreamWriter(localFs.create(file));
      try {
        for (int i = 0; i < length; i++) {
          writer.write(Integer.toString(i * 2));
          writer.write("\t");
          writer.write(Integer.toString(i));
          writer.write("\n");
        }
      } finally {
        writer.close();
      }

      // try splitting the file in a variety of sizes
      KeyValueTextInputFormat format = new KeyValueTextInputFormat();
      for (int i = 0; i < 3; i++) {
        int numSplits = random.nextInt(MAX_LENGTH / 20) + 1;
        LOG.debug("splitting: requesting = " + numSplits);
        List<InputSplit> splits = format.getSplits(job);
        LOG.debug("splitting: got =        " + splits.size());

        // check each split
        BitSet bits = new BitSet(length);
        for (int j = 0; j < splits.size(); j++) {
          LOG.debug("split["+j+"]= " + splits.get(j));
          TaskAttemptContext context = MapReduceTestUtil.
            createDummyMapTaskAttemptContext(job.getConfiguration());
          RecordReader<Text, Text> reader = format.createRecordReader(
            splits.get(j), context);
          Class<?> clazz = reader.getClass();
          assertEquals("reader class is KeyValueLineRecordReader.", 
            KeyValueLineRecordReader.class, clazz);
          MapContext<Text, Text, Text, Text> mcontext = 
            new MapContextImpl<Text, Text, Text, Text>(job.getConfiguration(), 
            context.getTaskAttemptID(), reader, null, null, 
            MapReduceTestUtil.createDummyReporter(), splits.get(j));
          reader.initialize(splits.get(j), mcontext);

          Text key = null;
          Text value = null;
          try {
            int count = 0;
            while (reader.nextKeyValue()) {
              key = reader.getCurrentKey();
              clazz = key.getClass();
              assertEquals("Key class is Text.", Text.class, clazz);
              value = reader.getCurrentValue();
              clazz = value.getClass();
              assertEquals("Value class is Text.", Text.class, clazz);
              final int k = Integer.parseInt(key.toString());
              final int v = Integer.parseInt(value.toString());
              assertEquals("Bad key", 0, k % 2);
              assertEquals("Mismatched key/value", k / 2, v);
              LOG.debug("read " + v);
              assertFalse("Key in multiple partitions.", bits.get(v));
              bits.set(v);
              count++;
            }
            LOG.debug("splits[" + j + "]=" + splits.get(j) +" count=" + count);
          } finally {
            reader.close();
          }
        }
        assertEquals("Some keys in no partition.", length, bits.cardinality());
      }

    }
  }

};