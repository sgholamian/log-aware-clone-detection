//,temp,TestCopyOutputFormat.java,57,75,temp,TestCopyOutputFormat.java,37,55
//,2
public class xxx {
  @Test
  public void testSetCommitDirectory() {
    try {
      Job job = Job.getInstance(new Configuration());
      Assert.assertEquals(null, CopyOutputFormat.getCommitDirectory(job));

      job.getConfiguration().set(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH, "");
      Assert.assertEquals(null, CopyOutputFormat.getCommitDirectory(job));

      Path directory = new Path("/tmp/test");
      CopyOutputFormat.setCommitDirectory(job, directory);
      Assert.assertEquals(directory, CopyOutputFormat.getCommitDirectory(job));
      Assert.assertEquals(directory.toString(), job.getConfiguration().
          get(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH));
    } catch (IOException e) {
      LOG.error("Exception encountered while running test", e);
      Assert.fail("Failed while testing for set Commit Directory");
    }
  }

};