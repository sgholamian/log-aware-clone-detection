//,temp,TestTextInputFormat.java,272,330,temp,TestTextInputFormat.java,197,270
//,3
public class xxx {
  @Test (timeout=900000)
  public void testSplitableCodecs2() throws IOException {
    JobConf conf = new JobConf(defaultConf);
    // Create the codec
    CompressionCodec codec = null;
    try {
      codec = (CompressionCodec)
      ReflectionUtils.newInstance(conf.getClassByName("org.apache.hadoop.io.compress.BZip2Codec"), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal codec!");
    }
    Path file = new Path(workDir, "test"+codec.getDefaultExtension());

    FileSystem localFs = FileSystem.getLocal(conf);
    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(conf, workDir);

    int length = 250000;
    LOG.info("creating; entries = " + length);
    // create a file with length entries
    Writer writer =
        new OutputStreamWriter(codec.createOutputStream(localFs.create(file)));
    try {
      for (int i = 0; i < length; i++) {
        writer.write(Integer.toString(i));
        writer.write("\n");
      }
    } finally {
      writer.close();
    }

    // Test split positions around a block boundary where the block does
    // not start on a byte boundary.
    for (long splitpos = 203418; splitpos < 203430; ++splitpos) {
      TextInputFormat format = new TextInputFormat();
      format.configure(conf);
      LOG.info("setting block size of the input file to " + splitpos);
      conf.setLong("mapreduce.input.fileinputformat.split.minsize", splitpos);
      LongWritable key = new LongWritable();
      Text value = new Text();
      InputSplit[] splits = format.getSplits(conf, 2);
      LOG.info("splitting: got =        " + splits.length);

      // check each split
      BitSet bits = new BitSet(length);
      for (int j = 0; j < splits.length; j++) {
        LOG.debug("split[" + j + "]= " + splits[j]);
        RecordReader<LongWritable, Text> reader =
            format.getRecordReader(splits[j], conf, Reporter.NULL);
        try {
          int counter = 0;
          while (reader.next(key, value)) {
            int v = Integer.parseInt(value.toString());
            LOG.debug("read " + v);
            if (bits.get(v)) {
              LOG.warn("conflict with " + v + " in split " + j +
                  " at position " + reader.getPos());
            }
            assertFalse("Key in multiple partitions.", bits.get(v));
            bits.set(v);
            counter++;
          }
          if (counter > 0) {
            LOG.info("splits[" + j + "]=" + splits[j] + " count=" + counter);
          } else {
            LOG.debug("splits[" + j + "]=" + splits[j] + " count=" + counter);
          }
        } finally {
          reader.close();
        }
      }
      assertEquals("Some keys in no partition.", length, bits.cardinality());
    }
  }

};