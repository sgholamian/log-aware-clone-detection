//,temp,TestCopyCommitter.java,178,232,temp,TestCopyCommitter.java,126,176
//,3
public class xxx {
  @Test
  public void testPreserveStatus() {
    TaskAttemptContext taskAttemptContext = getTaskAttemptContext(config);
    JobContext jobContext = new JobContextImpl(taskAttemptContext.getConfiguration(),
        taskAttemptContext.getTaskAttemptID().getJobID());
    Configuration conf = jobContext.getConfiguration();


    String sourceBase;
    String targetBase;
    FileSystem fs = null;
    try {
      OutputCommitter committer = new CopyCommitter(null, taskAttemptContext);
      fs = FileSystem.get(conf);
      FsPermission sourcePerm = new FsPermission((short) 511);
      FsPermission initialPerm = new FsPermission((short) 448);
      sourceBase = TestDistCpUtils.createTestSetup(fs, sourcePerm);
      targetBase = TestDistCpUtils.createTestSetup(fs, initialPerm);

      DistCpOptions options = new DistCpOptions(Arrays.asList(new Path(sourceBase)),
          new Path("/out"));
      options.preserve(FileAttribute.PERMISSION);
      options.appendToConf(conf);
      options.setTargetPathExists(false);
      
      CopyListing listing = new GlobbedCopyListing(conf, CREDENTIALS);
      Path listingFile = new Path("/tmp1/" + String.valueOf(rand.nextLong()));
      listing.buildListing(listingFile, options);

      conf.set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, targetBase);

      committer.commitJob(jobContext);
      if (!checkDirectoryPermissions(fs, targetBase, sourcePerm)) {
        Assert.fail("Permission don't match");
      }

      //Test for idempotent commit
      committer.commitJob(jobContext);
      if (!checkDirectoryPermissions(fs, targetBase, sourcePerm)) {
        Assert.fail("Permission don't match");
      }

    } catch (IOException e) {
      LOG.error("Exception encountered while testing for preserve status", e);
      Assert.fail("Preserve status failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp1");
      conf.unset(DistCpConstants.CONF_LABEL_PRESERVE_STATUS);
    }

  }

};