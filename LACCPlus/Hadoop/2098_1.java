//,temp,TestMRJobs.java,431,479,temp,TestMRJobs.java,199,243
//,3
public class xxx {
  @Test (timeout = 60000)
  public void testRandomWriter() throws IOException, InterruptedException,
      ClassNotFoundException {
    
    LOG.info("\n\n\nStarting testRandomWriter().");
    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    RandomTextWriterJob randomWriterJob = new RandomTextWriterJob();
    mrCluster.getConfig().set(RandomTextWriterJob.TOTAL_BYTES, "3072");
    mrCluster.getConfig().set(RandomTextWriterJob.BYTES_PER_MAP, "1024");
    Job job = randomWriterJob.createJob(mrCluster.getConfig());
    Path outputDir = new Path(OUTPUT_ROOT_DIR, "random-output");
    FileOutputFormat.setOutputPath(job, outputDir);
    job.setSpeculativeExecution(false);
    job.addFileToClassPath(APP_JAR); // The AppMaster jar itself.
    job.setJarByClass(RandomTextWriterJob.class);
    job.setMaxMapAttempts(1); // speed up failures
    job.submit();
    String trackingUrl = job.getTrackingURL();
    String jobId = job.getJobID().toString();
    boolean succeeded = job.waitForCompletion(true);
    Assert.assertTrue(succeeded);
    Assert.assertEquals(JobStatus.State.SUCCEEDED, job.getJobState());
    Assert.assertTrue("Tracking URL was " + trackingUrl +
                      " but didn't Match Job ID " + jobId ,
          trackingUrl.endsWith(jobId.substring(jobId.lastIndexOf("_")) + "/"));
    
    // Make sure there are three files in the output-dir
    
    RemoteIterator<FileStatus> iterator =
        FileContext.getFileContext(mrCluster.getConfig()).listStatus(
            outputDir);
    int count = 0;
    while (iterator.hasNext()) {
      FileStatus file = iterator.next();
      if (!file.getPath().getName()
          .equals(FileOutputCommitter.SUCCEEDED_FILE_NAME)) {
        count++;
      }
    }
    Assert.assertEquals("Number of part files is wrong!", 3, count);
    verifyRandomWriterCounters(job);

    // TODO later:  add explicit "isUber()" checks of some sort
  }

};