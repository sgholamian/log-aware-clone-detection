//,temp,TestSequenceFileAsBinaryOutputFormat.java,39,130,temp,TestMRSequenceFileAsBinaryOutputFormat.java,51,145
//,3
public class xxx {
  public void testBinary() throws IOException {
    JobConf job = new JobConf();
    FileSystem fs = FileSystem.getLocal(job);
    
    Path dir = 
      new Path(new Path(new Path(System.getProperty("test.build.data",".")), 
                        FileOutputCommitter.TEMP_DIR_NAME), "_" + attempt);
    Path file = new Path(dir, "testbinary.seq");
    Random r = new Random();
    long seed = r.nextLong();
    r.setSeed(seed);

    fs.delete(dir, true);
    if (!fs.mkdirs(dir)) { 
      fail("Failed to create output directory");
    }

    job.set(JobContext.TASK_ATTEMPT_ID, attempt);
    FileOutputFormat.setOutputPath(job, dir.getParent().getParent());
    FileOutputFormat.setWorkOutputPath(job, dir);

    SequenceFileAsBinaryOutputFormat.setSequenceFileOutputKeyClass(job, 
                                          IntWritable.class );
    SequenceFileAsBinaryOutputFormat.setSequenceFileOutputValueClass(job, 
                                          DoubleWritable.class ); 

    SequenceFileAsBinaryOutputFormat.setCompressOutput(job, true);
    SequenceFileAsBinaryOutputFormat.setOutputCompressionType(job, 
                                                       CompressionType.BLOCK);

    BytesWritable bkey = new BytesWritable();
    BytesWritable bval = new BytesWritable();


    RecordWriter <BytesWritable, BytesWritable> writer = 
      new SequenceFileAsBinaryOutputFormat().getRecordWriter(fs, 
                                                       job, file.toString(),
                                                       Reporter.NULL);

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
      writer.close(Reporter.NULL);
    }

    InputFormat<IntWritable,DoubleWritable> iformat =
                    new SequenceFileInputFormat<IntWritable,DoubleWritable>();
    int count = 0;
    r.setSeed(seed);
    DataInputBuffer buf = new DataInputBuffer();
    final int NUM_SPLITS = 3;
    SequenceFileInputFormat.addInputPath(job, file);
    LOG.info("Reading data by SequenceFileInputFormat");
    for (InputSplit split : iformat.getSplits(job, NUM_SPLITS)) {
      RecordReader<IntWritable,DoubleWritable> reader =
        iformat.getRecordReader(split, job, Reporter.NULL);
      try {
        int sourceInt;
        double sourceDouble;
        while (reader.next(iwritable, dwritable)) {
          sourceInt = r.nextInt();
          sourceDouble = r.nextDouble();
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