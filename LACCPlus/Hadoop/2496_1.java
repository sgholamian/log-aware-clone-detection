//,temp,TestFixedLengthInputFormat.java,281,392,temp,TestMRKeyValueTextInputFormat.java,156,253
//,3
public class xxx {
  private void runRandomTests(CompressionCodec codec) throws Exception {
    StringBuilder fileName = new StringBuilder("testFormat.txt");
    if (codec != null) {
      fileName.append(".gz");
    }
    localFs.delete(workDir, true);
    Path file = new Path(workDir, fileName.toString());
    int seed = new Random().nextInt();
    LOG.info("Seed = " + seed);
    Random random = new Random(seed);
    int MAX_TESTS = 20;
    LongWritable key;
    BytesWritable value;

    for (int i = 0; i < MAX_TESTS; i++) {
      LOG.info("----------------------------------------------------------");
      // Maximum total records of 999
      int totalRecords = random.nextInt(999)+1;
      // Test an empty file
      if (i == 8) {
         totalRecords = 0;
      }
      // Maximum bytes in a record of 100K
      int recordLength = random.nextInt(1024*100)+1;
      // For the 11th test, force a record length of 1
      if (i == 10) {
        recordLength = 1;
      }
      // The total bytes in the test file
      int fileSize = (totalRecords * recordLength);
      LOG.info("totalRecords=" + totalRecords + " recordLength="
          + recordLength);
      // Create the job 
      Job job = Job.getInstance(defaultConf);
      if (codec != null) {
        ReflectionUtils.setConf(codec, job.getConfiguration());
      }
      // Create the test file
      ArrayList<String> recordList =
          createFile(file, codec, recordLength, totalRecords);
      assertTrue(localFs.exists(file));
      //set the fixed length record length config property for the job
      FixedLengthInputFormat.setRecordLength(job.getConfiguration(),
          recordLength);

      int numSplits = 1;
      // Arbitrarily set number of splits.
      if (i > 0) {
        if (i == (MAX_TESTS-1)) {
          // Test a split size that is less than record len
          numSplits = (int)(fileSize/Math.floor(recordLength/2));
        } else {
          if (MAX_TESTS % i == 0) {
            // Let us create a split size that is forced to be 
            // smaller than the end file itself, (ensures 1+ splits)
            numSplits = fileSize/(fileSize - random.nextInt(fileSize));
          } else {
            // Just pick a random split size with no upper bound 
            numSplits = Math.max(1, fileSize/random.nextInt(Integer.MAX_VALUE));
          }
        }
        LOG.info("Number of splits set to: " + numSplits);
      }
      job.getConfiguration().setLong(
          "mapreduce.input.fileinputformat.split.maxsize", 
          (long)(fileSize/numSplits));

      // setup the input path
      FileInputFormat.setInputPaths(job, workDir);
      // Try splitting the file in a variety of sizes
      FixedLengthInputFormat format = new FixedLengthInputFormat();
      List<InputSplit> splits = format.getSplits(job);
      LOG.info("Actual number of splits = " + splits.size());
      // Test combined split lengths = total file size
      long recordOffset = 0;
      int recordNumber = 0;
      for (InputSplit split : splits) {
        TaskAttemptContext context = MapReduceTestUtil.
            createDummyMapTaskAttemptContext(job.getConfiguration());
        RecordReader<LongWritable, BytesWritable> reader = 
            format.createRecordReader(split, context);
        MapContext<LongWritable, BytesWritable, LongWritable, BytesWritable>
            mcontext =
            new MapContextImpl<LongWritable, BytesWritable, LongWritable,
            BytesWritable>(job.getConfiguration(), context.getTaskAttemptID(),
            reader, null, null, MapReduceTestUtil.createDummyReporter(), split);
        reader.initialize(split, mcontext);
        Class<?> clazz = reader.getClass();
        assertEquals("RecordReader class should be FixedLengthRecordReader:", 
            FixedLengthRecordReader.class, clazz);
        // Plow through the records in this split
        while (reader.nextKeyValue()) {
          key = reader.getCurrentKey();
          value = reader.getCurrentValue();
          assertEquals("Checking key", (long)(recordNumber*recordLength),
              key.get());
          String valueString = new String(value.getBytes(), 0,
              value.getLength());
          assertEquals("Checking record length:", recordLength,
              value.getLength());
          assertTrue("Checking for more records than expected:",
              recordNumber < totalRecords);
          String origRecord = recordList.get(recordNumber);
          assertEquals("Checking record content:", origRecord, valueString);
          recordNumber++;
        }
        reader.close();
      }
      assertEquals("Total original records should be total read records:",
          recordList.size(), recordNumber);
    }
  }

};