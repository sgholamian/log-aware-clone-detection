//,temp,TestWorker.java,938,972,temp,TestWorker.java,383,418
//,3
public class xxx {
  @Test
  public void minorWithAborted() throws Exception {
    LOG.debug("Starting minorWithAborted");
    Table t = newTable("default", "mtwb", false);

    addBaseFile(t, null, 20L, 20);
    addDeltaFile(t, null, 21L, 22L, 2);
    addDeltaFile(t, null, 23L, 25L, 3);
    addLengthFile(t, null, 23L, 25L, 3);
    addDeltaFile(t, null, 26L, 27L, 2);
    burnThroughTransactions("default", "mtwb", 27, null, new HashSet<Long>(Arrays.asList(24L, 25L)));

    CompactionRequest rqst = new CompactionRequest("default", "mtwb", CompactionType.MINOR);
    txnHandler.compact(rqst);

    startWorker();

    ShowCompactResponse rsp = txnHandler.showCompact(new ShowCompactRequest());
    List<ShowCompactResponseElement> compacts = rsp.getCompacts();
    Assert.assertEquals(1, compacts.size());
    Assert.assertEquals("ready for cleaning", compacts.get(0).getState());

    // There should still now be 6 directories in the location
    FileSystem fs = FileSystem.get(conf);
    FileStatus[] stat = fs.listStatus(new Path(t.getSd().getLocation()));
    Assert.assertEquals(6, stat.length);

    // Find the new delta file and make sure it has the right contents
    Arrays.sort(stat);
    Assert.assertEquals("base_20", stat[0].getPath().getName());
    Assert.assertEquals(makeDeleteDeltaDirNameCompacted(21, 27) + "_v0000028", stat[1].getPath().getName());
    Assert.assertEquals(makeDeltaDirName(21, 22), stat[2].getPath().getName());
    Assert.assertEquals(makeDeltaDirNameCompacted(21, 27) + "_v0000028", stat[3].getPath().getName());
    Assert.assertEquals(makeDeltaDirName(23, 25), stat[4].getPath().getName());
    Assert.assertEquals(makeDeltaDirName(26, 27), stat[5].getPath().getName());
  }

};