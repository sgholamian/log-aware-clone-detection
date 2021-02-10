//,temp,TestSequenceFileAsBinaryOutputFormat.java,39,130,temp,TestMRSequenceFileAsBinaryOutputFormat.java,51,145
//,3
public class xxx {
  public void testBinary() throws IOException, InterruptedException {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf);
    
    Path outdir = new Path(System.getProperty("test.build.data", "/tmp"),
                    "outseq");
    Random r = new Random();
    long seed = r.nextLong();
    r.setSeed(seed);

    FileOutputFormat.setOutputPath(job, outdir);

    SequenceFileAsBinaryOutputFormat.setSequenceFileOutputKeyClass(job, 
                                          IntWritable.class );
    SequenceFileAsBinaryOutputFormat.setSequenceFileOutputValueClass(job, 
                                          DoubleWritable.class ); 

    SequenceFileAsBinaryOutputFormat.setCompressOutput(job, true);
    SequenceFileAsBinaryOutputFormat.setOutputCompressionType(job, 
                                                       CompressionType.BLOCK);

    BytesWritable bkey = new BytesWritable();
    BytesWritable bval = new BytesWritable();

    TaskAttemptContext context = 
      MapReduceTestUtil.createDummyMapTaskAttemptContext(job.getConfiguration());
    OutputFormat<BytesWritable, BytesWritable> outputFormat = 
      new SequenceFileAsBinaryOutputFormat();
    OutputCommitter committer = outputFormat.getOutputCommitter(context);
    committer.setupJob(job);
    RecordWriter<BytesWritable, BytesWritable> writer = outputFormat.
      getRecordWriter(context);

    IntWritable iwritable = new IntWritable();
    DoubleWritable dwritable = new DoubleWritable();
    DataOutputBuffer outbuf = new DataOutputBuffer();
    LOG.info("Creating data by SequenceFileAsBinaryOutputFormat");
    try {
      for (int i = 0; i < RECORDS; ++i) {
        iwritable = new IntWritable(r.nextInt());
        iwritable.write(outbuf);
        bkey.set(outbuf.getData(), 0, outbuf.getLength());
        outbuf.reset();
        dwritable = new DoubleWritable(r.nextDouble());
        dwritable.write(outbuf);
        bval.set(outbuf.getData(), 0, outbuf.getLength());
        outbuf.reset();
        writer.write(bkey, bval);
      }
    } finally {
      writer.close(context);
    }
    committer.commitTask(context);
    committer.commitJob(job);

    InputFormat<IntWritable, DoubleWritable> iformat =
      new SequenceFileInputFormat<IntWritable, DoubleWritable>();
    int count = 0;
    r.setSeed(seed);
    SequenceFileInputFormat.setInputPaths(job, outdir);
    LOG.info("Reading data by SequenceFileInputFormat");
    for (InputSplit split : iformat.getSplits(job)) {
      RecordReader<IntWritable, DoubleWritable> reader =
        iformat.createRecordReader(split, context);
      MapContext<IntWritable, DoubleWritable, BytesWritable, BytesWritable> 
        mcontext = new MapContextImpl<IntWritable, DoubleWritable,
          BytesWritable, BytesWritable>(job.getConfiguration(), 
          context.getTaskAttemptID(), reader, null, null, 
          MapReduceTestUtil.createDummyReporter(), 
          split);
      reader.initialize(split, mcontext);
      try {
        int sourceInt;
        double sourceDouble;
        while (reader.nextKeyValue()) {
          sourceInt = r.nextInt();
          sourceDouble = r.nextDouble();
          iwritable = reader.getCurrentKey();
          dwritable = reader.getCurrentValue();
          assertEquals(
              "Keys don't match: " + "*" + iwritable.get() + ":" + 
                                           sourceInt + "*",
              sourceInt, iwritable.get());
          assertTrue(
              "Vals don't match: " + "*" + dwritable.get() + ":" +
                                           sourceDouble + "*",
              Double.compare(dwritable.get(), sourceDouble) == 0 );
          ++count;
        }
      } finally {
        reader.close();
      }
    }
    assertEquals("Some records not found", RECORDS, count);
  }

};