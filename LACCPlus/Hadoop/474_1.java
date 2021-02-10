//,temp,TestCopyCommitter.java,340,380,temp,TestCopyCommitter.java,300,338
//,3
public class xxx {
  @Test
  public void testAtomicCommitExistingFinal() {
    TaskAttemptContext taskAttemptContext = getTaskAttemptContext(config);
    JobContext jobContext = new JobContextImpl(taskAttemptContext.getConfiguration(),
        taskAttemptContext.getTaskAttemptID().getJobID());
    Configuration conf = jobContext.getConfiguration();


    String workPath = "/tmp1/" + String.valueOf(rand.nextLong());
    String finalPath = "/tmp1/" + String.valueOf(rand.nextLong());
    FileSystem fs = null;
    try {
      OutputCommitter committer = new CopyCommitter(null, taskAttemptContext);
      fs = FileSystem.get(conf);
      fs.mkdirs(new Path(workPath));
      fs.mkdirs(new Path(finalPath));

      conf.set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, workPath);
      conf.set(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH, finalPath);
      conf.setBoolean(DistCpConstants.CONF_LABEL_ATOMIC_COPY, true);

      Assert.assertTrue(fs.exists(new Path(workPath)));
      Assert.assertTrue(fs.exists(new Path(finalPath)));
      try {
        committer.commitJob(jobContext);
        Assert.fail("Should not be able to atomic-commit to pre-existing path.");
      } catch(Exception exception) {
        Assert.assertTrue(fs.exists(new Path(workPath)));
        Assert.assertTrue(fs.exists(new Path(finalPath)));
        LOG.info("Atomic-commit Test pass.");
      }

    } catch (IOException e) {
      LOG.error("Exception encountered while testing for atomic commit.", e);
      Assert.fail("Atomic commit failure");
    } finally {
      TestDistCpUtils.delete(fs, workPath);
      TestDistCpUtils.delete(fs, finalPath);
      conf.setBoolean(DistCpConstants.CONF_LABEL_ATOMIC_COPY, false);
    }
  }

};