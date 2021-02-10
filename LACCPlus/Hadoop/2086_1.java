//,temp,TestCopyCommitter.java,234,298,temp,TestCopyCommitter.java,178,232
//,3
public class xxx {
  @Test
  public void testDeleteMissingFlatInterleavedFiles() {
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
      sourceBase = "/tmp1/" + String.valueOf(rand.nextLong());
      targetBase = "/tmp1/" + String.valueOf(rand.nextLong());
      TestDistCpUtils.createFile(fs, sourceBase + "/1");
      TestDistCpUtils.createFile(fs, sourceBase + "/3");
      TestDistCpUtils.createFile(fs, sourceBase + "/4");
      TestDistCpUtils.createFile(fs, sourceBase + "/5");
      TestDistCpUtils.createFile(fs, sourceBase + "/7");
      TestDistCpUtils.createFile(fs, sourceBase + "/8");
      TestDistCpUtils.createFile(fs, sourceBase + "/9");

      TestDistCpUtils.createFile(fs, targetBase + "/2");
      TestDistCpUtils.createFile(fs, targetBase + "/4");
      TestDistCpUtils.createFile(fs, targetBase + "/5");
      TestDistCpUtils.createFile(fs, targetBase + "/7");
      TestDistCpUtils.createFile(fs, targetBase + "/9");
      TestDistCpUtils.createFile(fs, targetBase + "/A");

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
      Assert.assertEquals(fs.listStatus(new Path(targetBase)).length, 4);

      //Test for idempotent commit
      committer.commitJob(jobContext);
      if (!TestDistCpUtils.checkIfFoldersAreInSync(fs, targetBase, sourceBase)) {
        Assert.fail("Source and target folders are not in sync");
      }
      Assert.assertEquals(fs.listStatus(new Path(targetBase)).length, 4);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing for delete missing", e);
      Assert.fail("Delete missing failure");
    } finally {
      TestDistCpUtils.delete(fs, "/tmp1");
      conf.set(DistCpConstants.CONF_LABEL_DELETE_MISSING, "false");
    }

  }

};