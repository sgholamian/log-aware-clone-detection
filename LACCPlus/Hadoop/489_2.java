//,temp,TestSequenceFileAsTextInputFormat.java,37,114,temp,TestKeyValueTextInputFormat.java,51,131
//,3
public class xxx {
  public void testFormat() throws Exception {
    JobConf job = new JobConf();
    Path file = new Path(workDir, "test.txt");

    // A reporter that does nothing
    Reporter reporter = Reporter.NULL;
    
    int seed = new Random().nextInt();
    LOG.info("seed = "+seed);
    Random random = new Random(seed);

    localFs.delete(workDir, true);
    FileInputFormat.setInputPaths(job, workDir);

    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length+= random.nextInt(MAX_LENGTH/10)+1) {

      LOG.debug("creating; entries = " + length);

      // create a file with length entries
      Writer writer = new OutputStreamWriter(localFs.create(file));
      try {
        for (int i = 0; i < length; i++) {
          writer.write(Integer.toString(i*2));
          writer.write("\t");
          writer.write(Integer.toString(i));
          writer.write("\n");
        }
      } finally {
        writer.close();
      }

      // try splitting the file in a variety of sizes
      KeyValueTextInputFormat format = new KeyValueTextInputFormat();
      format.configure(job);
      for (int i = 0; i < 3; i++) {
        int numSplits = random.nextInt(MAX_LENGTH/20)+1;
        LOG.debug("splitting: requesting = " + numSplits);
        InputSplit[] splits = format.getSplits(job, numSplits);
        LOG.debug("splitting: got =        " + splits.length);

        // check each split
        BitSet bits = new BitSet(length);
        for (int j = 0; j < splits.length; j++) {
          LOG.debug("split["+j+"]= " + splits[j]);
          RecordReader<Text, Text> reader =
            format.getRecordReader(splits[j], job, reporter);
          Class readerClass = reader.getClass();
          assertEquals("reader class is KeyValueLineRecordReader.", KeyValueLineRecordReader.class, readerClass);        

          Text key = reader.createKey();
          Class keyClass = key.getClass();
          Text value = reader.createValue();
          Class valueClass = value.getClass();
          assertEquals("Key class is Text.", Text.class, keyClass);
          assertEquals("Value class is Text.", Text.class, valueClass);
          try {
            int count = 0;
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
              count++;
            }
            LOG.debug("splits["+j+"]="+splits[j]+" count=" + count);
          } finally {
            reader.close();
          }
        }
        assertEquals("Some keys in no partition.", length, bits.cardinality());
      }

    }
  }

};