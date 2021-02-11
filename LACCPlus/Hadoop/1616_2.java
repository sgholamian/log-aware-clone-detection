//,temp,TestFixedLengthInputFormat.java,281,392,temp,TestMRKeyValueTextInputFormat.java,158,255
//,3
public class xxx {
  @Test
  public void testSplitableCodecs() throws Exception {
    final Job job = Job.getInstance(defaultConf);
    final Configuration conf = job.getConfiguration();

    // Create the codec
    CompressionCodec codec = null;
    try {
      codec = (CompressionCodec)
      ReflectionUtils.newInstance(conf.getClassByName("org.apache.hadoop.io.compress.BZip2Codec"), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal codec!");
    }
    Path file = new Path(workDir, "test"+codec.getDefaultExtension());

    int seed = new Random().nextInt();
    LOG.info("seed = " + seed);
    Random random = new Random(seed);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(job, workDir);

    final int MAX_LENGTH = 500000;
    FileInputFormat.setMaxInputSplitSize(job, MAX_LENGTH / 20);
    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length += random.nextInt(MAX_LENGTH / 4) + 1) {

      LOG.info("creating; entries = " + length);

      // create a file with length entries
      Writer writer =
        new OutputStreamWriter(codec.createOutputStream(localFs.create(file)));
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
      assertTrue("KVTIF claims not splittable", format.isSplitable(job, file));
      for (int i = 0; i < 3; i++) {
        int numSplits = random.nextInt(MAX_LENGTH / 2000) + 1;
        LOG.info("splitting: requesting = " + numSplits);
        List<InputSplit> splits = format.getSplits(job);
        LOG.info("splitting: got =        " + splits.size());

        // check each split
        BitSet bits = new BitSet(length);
        for (int j = 0; j < splits.size(); j++) {
          LOG.debug("split["+j+"]= " + splits.get(j));
          TaskAttemptContext context = MapReduceTestUtil.
            createDummyMapTaskAttemptContext(job.getConfiguration());
          RecordReader<Text, Text> reader = format.createRecordReader(
            splits.get(j), context);
          Class<?> clazz = reader.getClass();
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
              value = reader.getCurrentValue();
              final int k = Integer.parseInt(key.toString());
              final int v = Integer.parseInt(value.toString());
              assertEquals("Bad key", 0, k % 2);
              assertEquals("Mismatched key/value", k / 2, v);
              LOG.debug("read " + k + "," + v);
              assertFalse(k + "," + v + " in multiple partitions.",bits.get(v));
              bits.set(v);
              count++;
            }
            if (count > 0) {
              LOG.info("splits["+j+"]="+splits.get(j)+" count=" + count);
            } else {
              LOG.debug("splits["+j+"]="+splits.get(j)+" count=" + count);
            }
          } finally {
            reader.close();
          }
        }
        assertEquals("Some keys in no partition.", length, bits.cardinality());
      }

    }
  }

};