//,temp,TestWorker.java,420,472,temp,TestWorker.java,289,341
//,3
public class xxx {
  @Test
  public void minorTableWithBase() throws Exception {
    LOG.debug("Starting minorTableWithBase");
    Table t = newTable("default", "mtwb", false);

    addBaseFile(t, null, 20L, 20);
    addDeltaFile(t, null, 21L, 22L, 2);
    addDeltaFile(t, null, 23L, 24L, 2);

    burnThroughTransactions("default", "mtwb", 25);

    CompactionRequest rqst = new CompactionRequest("default", "mtwb", CompactionType.MINOR);
    txnHandler.compact(rqst);

    startWorker();//adds delta and delete_delta

    ShowCompactResponse rsp = txnHandler.showCompact(new ShowCompactRequest());
    List<ShowCompactResponseElement> compacts = rsp.getCompacts();
    Assert.assertEquals(1, compacts.size());
    Assert.assertEquals("ready for cleaning", compacts.get(0).getState());

    // There should still now be 5 directories in the location
    FileSystem fs = FileSystem.get(conf);
    FileStatus[] stat = fs.listStatus(new Path(t.getSd().getLocation()));
    Assert.assertEquals(5, stat.length);

    // Find the new delta file and make sure it has the right contents
    boolean sawNewDelta = false;
    for (int i = 0; i < stat.length; i++) {
      if (stat[i].getPath().getName().equals(makeDeltaDirNameCompacted(21, 24) + "_v0000026")) {
        sawNewDelta = true;
        FileStatus[] buckets = fs.listStatus(stat[i].getPath(), AcidUtils.hiddenFileFilter);
        Assert.assertEquals(2, buckets.length);
        Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertEquals(104L, buckets[0].getLen());
        Assert.assertEquals(104L, buckets[1].getLen());
      }
      if (stat[i].getPath().getName().equals(makeDeleteDeltaDirNameCompacted(21, 24) + "_v0000026")) {
        sawNewDelta = true;
        FileStatus[] buckets = fs.listStatus(stat[i].getPath(), AcidUtils.hiddenFileFilter);
        Assert.assertEquals(2, buckets.length);
        Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertEquals(104L, buckets[0].getLen());
        Assert.assertEquals(104L, buckets[1].getLen());
      }
      else {
        LOG.debug("This is not the delta file you are looking for " + stat[i].getPath().getName());
      }
    }
    Assert.assertTrue(toString(stat), sawNewDelta);
  }

};