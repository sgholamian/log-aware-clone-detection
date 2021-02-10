//,temp,TestTextInputFormat.java,149,241,temp,TestKeyValueTextInputFormat.java,51,131
//,3
public class xxx {
  @Test (timeout=900000)
  public void testSplitableCodecs() throws IOException {
    JobConf conf = new JobConf(defaultConf);
    int seed = new Random().nextInt();
    // Create the codec
    CompressionCodec codec = null;
    try {
      codec = (CompressionCodec)
      ReflectionUtils.newInstance(conf.getClassByName("org.apache.hadoop.io.compress.BZip2Codec"), conf);
    } catch (ClassNotFoundException cnfe) {
      throw new IOException("Illegal codec!");
    }
    Path file = new Path(workDir, "test"+codec.getDefaultExtension());

    // A reporter that does nothing
    Reporter reporter = Reporter.NULL;
    LOG.info("seed = "+seed);
    Random random = new Random(seed);
    FileSystem localFs = FileSystem.getLocal(conf);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(conf, workDir);

    final int MAX_LENGTH = 500000;

    // for a variety of lengths
    for (int length = MAX_LENGTH / 2; length < MAX_LENGTH;
        length += random.nextInt(MAX_LENGTH / 4)+1) {

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

      // try splitting the file in a variety of sizes
      TextInputFormat format = new TextInputFormat();
      format.configure(conf);
      LongWritable key = new LongWritable();
      Text value = new Text();
      for (int i = 0; i < 3; i++) {
        int numSplits = random.nextInt(MAX_LENGTH/2000)+1;
        LOG.info("splitting: requesting = " + numSplits);
        InputSplit[] splits = format.getSplits(conf, numSplits);
        LOG.info("splitting: got =        " + splits.length);



        // check each split
        BitSet bits = new BitSet(length);
        for (int j = 0; j < splits.length; j++) {
          LOG.debug("split["+j+"]= " + splits[j]);
          RecordReader<LongWritable, Text> reader =
            format.getRecordReader(splits[j], conf, reporter);
          try {
            int counter = 0;
            while (reader.next(key, value)) {
              int v = Integer.parseInt(value.toString());
              LOG.debug("read " + v);

              if (bits.get(v)) {
                LOG.warn("conflict with " + v +
                    " in split " + j +
                    " at position "+reader.getPos());
              }
              assertFalse("Key in multiple partitions.", bits.get(v));
              bits.set(v);
              counter++;
            }
            if (counter > 0) {
              LOG.info("splits["+j+"]="+splits[j]+" count=" + counter);
            } else {
              LOG.debug("splits["+j+"]="+splits[j]+" count=" + counter);
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