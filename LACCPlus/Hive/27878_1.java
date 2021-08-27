//,temp,TestWorker.java,762,804,temp,TestWorker.java,526,568
//,3
public class xxx {
  @Test
  public void majorTableLegacy() throws Exception {
    LOG.debug("Starting majorTableLegacy");
    Table t = newTable("default", "matl", false);

    addLegacyFile(t, null, 20);
    addDeltaFile(t, null, 21L, 22L, 2);
    addDeltaFile(t, null, 23L, 24L, 2);

    burnThroughTransactions("default", "matl", 25);

    CompactionRequest rqst = new CompactionRequest("default", "matl", CompactionType.MAJOR);
    txnHandler.compact(rqst);

    startWorker();

    ShowCompactResponse rsp = txnHandler.showCompact(new ShowCompactRequest());
    List<ShowCompactResponseElement> compacts = rsp.getCompacts();
    Assert.assertEquals(1, compacts.size());
    Assert.assertEquals("ready for cleaning", compacts.get(0).getState());

    // There should still now be 5 directories in the location
    FileSystem fs = FileSystem.get(conf);
    FileStatus[] stat = fs.listStatus(new Path(t.getSd().getLocation()));
    //Assert.assertEquals(4, stat.length);

    // Find the new delta file and make sure it has the right contents
    boolean sawNewBase = false;
    for (int i = 0; i < stat.length; i++) {
      if (stat[i].getPath().getName().equals("base_0000024_v0000026")) {
        sawNewBase = true;
        FileStatus[] buckets = fs.listStatus(stat[i].getPath(), FileUtils.HIDDEN_FILES_PATH_FILTER);
        Assert.assertEquals(2, buckets.length);
        Assert.assertTrue(buckets[0].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertTrue(buckets[1].getPath().getName().matches("bucket_0000[01]"));
        Assert.assertEquals(624L, buckets[0].getLen());
        Assert.assertEquals(624L, buckets[1].getLen());
      } else {
        LOG.debug("This is not the file you are looking for " + stat[i].getPath().getName());
      }
    }
    Assert.assertTrue(toString(stat), sawNewBase);
  }

};