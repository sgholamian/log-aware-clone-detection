//,temp,IntegrationTestLoadAndVerify.java,361,386,temp,IntegrationTestLoadAndVerify.java,333,355
//,3
public class xxx {
  protected Job doLoad(Configuration conf, HTableDescriptor htd) throws Exception {
    Path outputDir = getTestDir(TEST_NAME, "load-output");
    LOG.info("Load output dir: " + outputDir);

    NMapInputFormat.setNumMapTasks(conf, conf.getInt(NUM_MAP_TASKS_KEY, NUM_MAP_TASKS_DEFAULT));
    conf.set(TABLE_NAME_KEY, htd.getTableName().getNameAsString());

    Job job = Job.getInstance(conf);
    job.setJobName(TEST_NAME + " Load for " + htd.getTableName());
    job.setJarByClass(this.getClass());
    setMapperClass(job);
    job.setInputFormatClass(NMapInputFormat.class);
    job.setNumReduceTasks(0);
    setJobScannerConf(job);
    FileOutputFormat.setOutputPath(job, outputDir);

    TableMapReduceUtil.addDependencyJars(job);

    TableMapReduceUtil.addDependencyJarsForClasses(job.getConfiguration(), AbstractHBaseTool.class);
    TableMapReduceUtil.initCredentials(job);
    assertTrue(job.waitForCompletion(true));
    return job;
  }

};