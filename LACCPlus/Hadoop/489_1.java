//,temp,TestSequenceFileAsTextInputFormat.java,37,114,temp,TestKeyValueTextInputFormat.java,51,131
//,3
public class xxx {
  public void testFormat() throws Exception {
    JobConf job = new JobConf(conf);
    FileSystem fs = FileSystem.getLocal(conf);
    Path dir = new Path(System.getProperty("test.build.data",".") + "/mapred");
    Path file = new Path(dir, "test.seq");
    
    Reporter reporter = Reporter.NULL;
    
    int seed = new Random().nextInt();
    //LOG.info("seed = "+seed);
    Random random = new Random(seed);

    fs.delete(dir, true);

    FileInputFormat.setInputPaths(job, dir);

    // for a variety of lengths
    for (int length = 0; length < MAX_LENGTH;
         length+= random.nextInt(MAX_LENGTH/10)+1) {

      //LOG.info("creating; entries = " + length);

      // create a file with length entries
      SequenceFile.Writer writer =
        SequenceFile.createWriter(fs, conf, file,
                                  IntWritable.class, LongWritable.class);
      try {
        for (int i = 0; i < length; i++) {
          IntWritable key = new IntWritable(i);
          LongWritable value = new LongWritable(10 * i);
          writer.append(key, value);
        }
      } finally {
        writer.close();
      }

      // try splitting the file in a variety of sizes
      InputFormat<Text, Text> format =
        new SequenceFileAsTextInputFormat();
      
      for (int i = 0; i < 3; i++) {
        int numSplits =
          random.nextInt(MAX_LENGTH/(SequenceFile.SYNC_INTERVAL/20))+1;
        //LOG.info("splitting: requesting = " + numSplits);
        InputSplit[] splits = format.getSplits(job, numSplits);
        //LOG.info("splitting: got =        " + splits.length);

        // check each split
        BitSet bits = new BitSet(length);
        for (int j = 0; j < splits.length; j++) {
          RecordReader<Text, Text> reader =
            format.getRecordReader(splits[j], job, reporter);
          Class readerClass = reader.getClass();
          assertEquals("reader class is SequenceFileAsTextRecordReader.", SequenceFileAsTextRecordReader.class, readerClass);        
          Text value = reader.createValue();
          Text key = reader.createKey();
          try {
            int count = 0;
            while (reader.next(key, value)) {
              // if (bits.get(key.get())) {
              // LOG.info("splits["+j+"]="+splits[j]+" : " + key.get());
              // LOG.info("@"+reader.getPos());
              // }
              int keyInt = Integer.parseInt(key.toString());
              assertFalse("Key in multiple partitions.", bits.get(keyInt));
              bits.set(keyInt);
              count++;
            }
            //LOG.info("splits["+j+"]="+splits[j]+" count=" + count);
          } finally {
            reader.close();
          }
        }
        assertEquals("Some keys in no partition.", length, bits.cardinality());
      }

    }
  }

};