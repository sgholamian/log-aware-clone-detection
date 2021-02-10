//,temp,TestTextInputFormat.java,272,330,temp,TestTextInputFormat.java,197,270
//,3
public class xxx {
  private void verifyPartitions(int length, int numSplits, Path file,
      CompressionCodec codec, JobConf conf) throws IOException {

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
    LOG.info("splitting: requesting = " + numSplits);
    InputSplit[] splits = format.getSplits(conf, numSplits);
    LOG.info("splitting: got =        " + splits.length);


    // check each split
    BitSet bits = new BitSet(length);
    for (int j = 0; j < splits.length; j++) {
      LOG.debug("split["+j+"]= " + splits[j]);
      RecordReader<LongWritable, Text> reader =
              format.getRecordReader(splits[j], conf, Reporter.NULL);
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

};