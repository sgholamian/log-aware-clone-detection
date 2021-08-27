//,temp,TestWorker.java,806,845,temp,TestWorker.java,708,749
//,3
public class xxx {
  @Test
  public void majorTableNoBase() throws Exception {
    LOG.debug("Starting majorTableNoBase");
    Table t = newTable("default", "matnb", false);

    addDeltaFile(t, null, 1L, 2L, 2);
    addDeltaFile(t, null, 3L, 4L, 2);

    burnThroughTransactions("default", "matnb", 4);

    CompactionRequest rqst = new CompactionRequest("default", "matnb", CompactionType.MAJOR);
    txnHandler.compact(rqst);

    startWorker();

    ShowCompactResponse rsp = txnHandler.showCompact(new ShowCompactRequest());
    List<ShowCompactResponseElement> compacts = rsp.getCompacts();
    Assert.assertEquals(1, compacts.size());
    Assert.assertEquals("ready for cleaning", compacts.get(0).getState());

    // There should now be 3 directories in the location
    FileSystem fs = FileSystem.get(conf);
    FileStatus[] stat = fs.listStatus(new Path(t.getSd().getLocation()));
    Assert.assertEquals(3, stat.length);

    // Find the new delta file and make sure it has the right contents
    boolean sawNewBase = false;
    for (int i = 0; i < stat.length; i++) {
      if (stat[i].getPath().getName().equals("base_0000004_v0000005")) {
        sawNewBase = true;
        FileStatus[] buckets = fs.listStatus(stat[i].getPath(), FileUtils.HIDDEN_FILES_PATH_FILTER);
        Assert.assertEquals(2, buckets.length);
        Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertEquals(104L, buckets[0].getLen());
        Assert.assertEquals(104L, buckets[1].getLen());
      } else {
        LOG.debug("This is not the file you are looking for " + stat[i].getPath().getName());
      }
    }
    Assert.assertTrue(toString(stat), sawNewBase);
  }

};