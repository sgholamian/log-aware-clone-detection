//,temp,TestRenameWithSnapshots.java,293,317,temp,TestRenameWithSnapshots.java,267,291
//,3
public class xxx {
  @Test (timeout=60000)
  public void testRenameFileInSubDirOfDirWithSnapshot() throws Exception {
    final Path sub2 = new Path(sub1, "sub2");
    final Path sub2file1 = new Path(sub2, "sub2file1");
    final Path sub2file2 = new Path(sub2, "sub2file2");
    final String sub1snap1 = "sub1snap1";
    
    hdfs.mkdirs(sub1);
    hdfs.mkdirs(sub2);
    DFSTestUtil.createFile(hdfs, sub2file1, BLOCKSIZE, REPL, SEED);
    SnapshotTestHelper.createSnapshot(hdfs, sub1, sub1snap1);

    // Rename the file in the subdirectory.
    hdfs.rename(sub2file1, sub2file2);

    // Query the diff report and make sure it looks as expected.
    SnapshotDiffReport diffReport = hdfs.getSnapshotDiffReport(sub1, sub1snap1,
        "");
    LOG.info("DiffList is \n\"" + diffReport.toString() + "\"");
    List<DiffReportEntry> entries = diffReport.getDiffList();
    assertTrue(existsInDiffReport(entries, DiffType.MODIFY, sub2.getName(),
        null));
    assertTrue(existsInDiffReport(entries, DiffType.RENAME, sub2.getName()
        + "/" + sub2file1.getName(), sub2.getName() + "/" + sub2file2.getName()));
  }

};