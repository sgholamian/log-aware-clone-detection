//,temp,TestCopyOutputFormat.java,57,75,temp,TestCopyOutputFormat.java,37,55
//,2
public class xxx {
  @Test
  public void testSetWorkingDirectory() {
    try {
      Job job = Job.getInstance(new Configuration());
      Assert.assertEquals(null, CopyOutputFormat.getWorkingDirectory(job));

      job.getConfiguration().set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, "");
      Assert.assertEquals(null, CopyOutputFormat.getWorkingDirectory(job));

      Path directory = new Path("/tmp/test");
      CopyOutputFormat.setWorkingDirectory(job, directory);
      Assert.assertEquals(directory, CopyOutputFormat.getWorkingDirectory(job));
      Assert.assertEquals(directory.toString(), job.getConfiguration().
          get(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH));
    } catch (IOException e) {
      LOG.error("Exception encountered while running test", e);
      Assert.fail("Failed while testing for set Working Directory");
    }
  }

};