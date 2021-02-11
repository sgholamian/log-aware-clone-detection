//,temp,TestRenameWithSnapshots.java,281,305,temp,TestRenameWithSnapshots.java,255,279
//,3
public class xxx {
  @Test (timeout=60000)
  public void testRenameDirectoryInSnapshot() throws Exception {
    final Path sub2 = new Path(sub1, "sub2");
    final Path sub3 = new Path(sub1, "sub3");
    final Path sub2file1 = new Path(sub2, "sub2file1");
    final String sub1snap1 = "sub1snap1";
    
    hdfs.mkdirs(sub1);
    hdfs.mkdirs(sub2);
    DFSTestUtil.createFile(hdfs, sub2file1, BLOCKSIZE, REPL, SEED);
    SnapshotTestHelper.createSnapshot(hdfs, sub1, sub1snap1);
    
    // First rename the sub-directory.
    hdfs.rename(sub2, sub3);
    
    // Query the diff report and make sure it looks as expected.
    SnapshotDiffReport diffReport = hdfs.getSnapshotDiffReport(sub1, sub1snap1,
        "");
    LOG.info("DiffList is \n\"" + diffReport.toString() + "\"");
    List<DiffReportEntry> entries = diffReport.getDiffList();
    assertEquals(2, entries.size());
    assertTrue(existsInDiffReport(entries, DiffType.MODIFY, "", null));
    assertTrue(existsInDiffReport(entries, DiffType.RENAME, sub2.getName(),
        sub3.getName()));
  }

};