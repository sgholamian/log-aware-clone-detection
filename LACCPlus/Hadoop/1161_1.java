//,temp,TestMROldApiJobs.java,127,154,temp,TestMROldApiJobs.java,98,125
//,2
public class xxx {
  @Test
  public void testJobFail() throws IOException, InterruptedException,
      ClassNotFoundException { 

    LOG.info("\n\n\nStarting testJobFail().");

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    JobConf conf = new JobConf(mrCluster.getConfig());
    
    Path in = new Path(mrCluster.getTestWorkDir().getAbsolutePath(),
      "fail-in");
    Path out = new Path(mrCluster.getTestWorkDir().getAbsolutePath(),
      "fail-out"); 
    runJobFail(conf, in, out);
    
    FileSystem fs = FileSystem.get(conf);
    Assert.assertTrue(fs.exists(new Path(out, CustomOutputCommitter.JOB_SETUP_FILE_NAME)));
    Assert.assertTrue(fs.exists(new Path(out, CustomOutputCommitter.JOB_ABORT_FILE_NAME)));
    Assert.assertFalse(fs.exists(new Path(out, CustomOutputCommitter.JOB_COMMIT_FILE_NAME)));
    Assert.assertTrue(fs.exists(new Path(out, CustomOutputCommitter.TASK_SETUP_FILE_NAME)));
    Assert.assertTrue(fs.exists(new Path(out, CustomOutputCommitter.TASK_ABORT_FILE_NAME)));
    Assert.assertFalse(fs.exists(new Path(out, CustomOutputCommitter.TASK_COMMIT_FILE_NAME)));
  }

};