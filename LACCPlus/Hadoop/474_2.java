//,temp,TestCopyCommitter.java,340,380,temp,TestCopyCommitter.java,300,338
//,3
public class xxx {
  @Test
  public void testAtomicCommitMissingFinal() {
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

      conf.set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, workPath);
      conf.set(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH, finalPath);
      conf.setBoolean(DistCpConstants.CONF_LABEL_ATOMIC_COPY, true);

      Assert.assertTrue(fs.exists(new Path(workPath)));
      Assert.assertFalse(fs.exists(new Path(finalPath)));
      committer.commitJob(jobContext);
      Assert.assertFalse(fs.exists(new Path(workPath)));
      Assert.assertTrue(fs.exists(new Path(finalPath)));

      //Test for idempotent commit
      committer.commitJob(jobContext);
      Assert.assertFalse(fs.exists(new Path(workPath)));
      Assert.assertTrue(fs.exists(new Path(finalPath)));

    } catch (IOException e) {
      LOG.error("Exception encountered while testing for preserve status", e);
      Assert.fail("Atomic commit failure");
    } finally {
      TestDistCpUtils.delete(fs, workPath);
      TestDistCpUtils.delete(fs, finalPath);
      conf.setBoolean(DistCpConstants.CONF_LABEL_ATOMIC_COPY, false);
    }
  }

};