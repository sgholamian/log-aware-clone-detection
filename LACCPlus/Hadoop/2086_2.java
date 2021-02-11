//,temp,TestCopyCommitter.java,234,298,temp,TestCopyCommitter.java,178,232
//,3
public class xxx {
  @Test
  public void testDeleteMissing() {
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
      sourceBase = TestDistCpUtils.createTestSetup(fs, FsPermission.getDefault());
      targetBase = TestDistCpUtils.createTestSetup(fs, FsPermission.getDefault());
      String targetBaseAdd = TestDistCpUtils.createTestSetup(fs, FsPermission.getDefault());
      fs.rename(new Path(targetBaseAdd), new Path(targetBase));

      DistCpOptions options = new DistCpOptions(Arrays.asList(new Path(sourceBase)),
          new Path("/out"));
      options.setSyncFolder(true);
      options.setDeleteMissing(true);
      options.appendToConf(conf);

      CopyListing listing = new GlobbedCopyListing(conf, CREDENTIALS);
      Path listingFile = new Path("/tmp1/" + String.valueOf(rand.nextLong()));
      listing.buildListing(listingFile, options);

      conf.set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, targetBase);
      conf.set(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH, targetBase);

      committer.commitJob(jobContext);
      if (!TestDistCpUtils.checkIfFoldersAreInSync(fs, targetBase, sourceBase)) {
        Assert.fail("Source and target folders are not in sync");
      }
      if (!TestDistCpUtils.checkIfFoldersAreInSync(fs, sourceBase, targetBase)) {
        Assert.fail("Source and target folders are not in sync");
      }

      //Test for idempotent commit
      committer.commitJob(jobContext);
      if (!TestDistCpUtils.checkIfFoldersAreInSync(fs, targetBase, sourceBase)) {
        Assert.fail("Source and target folders are not in sync");
      }
      if (!TestDistCpUtils.checkIfFoldersAreInSync(fs, sourceBase, targetBase)) {
        Assert.fail("Source and target folders are not in sync");
      }
    } catch (Throwable e) {
      LOG.error("Exception encountered while testing for delete missing", e);
      Assert.fail("Delete missing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp1");
      conf.set(DistCpConstants.CONF_LABEL_DELETE_MISSING, "false");
    }
  }

};