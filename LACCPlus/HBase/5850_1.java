//,temp,IntegrationTestLoadAndVerify.java,361,386,temp,IntegrationTestLoadAndVerify.java,333,355
//,3
public class xxx {
  protected void doVerify(Configuration conf, HTableDescriptor htd) throws Exception {
    Path outputDir = getTestDir(TEST_NAME, "verify-output");
    LOG.info("Verify output dir: " + outputDir);

    Job job = Job.getInstance(conf);
    job.setJarByClass(this.getClass());
    job.setJobName(TEST_NAME + " Verification for " + htd.getTableName());
    setJobScannerConf(job);

    Scan scan = new Scan();

    TableMapReduceUtil.initTableMapperJob(
        htd.getTableName().getNameAsString(), scan, VerifyMapper.class,
        BytesWritable.class, BytesWritable.class, job);
    TableMapReduceUtil.addDependencyJarsForClasses(job.getConfiguration(), AbstractHBaseTool.class);
    int scannerCaching = conf.getInt("verify.scannercaching", SCANNER_CACHING);
    TableMapReduceUtil.setScannerCaching(job, scannerCaching);

    job.setReducerClass(VerifyReducer.class);
    job.setNumReduceTasks(conf.getInt(NUM_REDUCE_TASKS_KEY, NUM_REDUCE_TASKS_DEFAULT));
    FileOutputFormat.setOutputPath(job, outputDir);
    assertTrue(job.waitForCompletion(true));

    long numOutputRecords = job.getCounters().findCounter(Counters.ROWS_WRITTEN).getValue();
    assertEquals(0, numOutputRecords);
  }

};