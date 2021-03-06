//,temp,DFSTestUtil.java,2394,2421,temp,TestSnapshotDiffReport.java,1397,1423
//,3
public class xxx {
  private void verifyDiffReportForGivenReport(Path dirPath, String from,
      String to, SnapshotDiffReport report, DiffReportEntry... entries)
      throws IOException {
    // reverse the order of from and to
    SnapshotDiffReport inverseReport =
        hdfs.getSnapshotDiffReport(dirPath, to, from);
    LOG.info(report.toString());
    LOG.info(inverseReport.toString() + "\n");

    assertEquals(entries.length, report.getDiffList().size());
    assertEquals(entries.length, inverseReport.getDiffList().size());

    for (DiffReportEntry entry : entries) {
      if (entry.getType() == DiffType.MODIFY) {
        assertTrue(report.getDiffList().contains(entry));
        assertTrue(inverseReport.getDiffList().contains(entry));
      } else if (entry.getType() == DiffType.DELETE) {
        assertTrue(report.getDiffList().contains(entry));
        assertTrue(inverseReport.getDiffList().contains(
            new DiffReportEntry(DiffType.CREATE, entry.getSourcePath())));
      } else if (entry.getType() == DiffType.CREATE) {
        assertTrue(report.getDiffList().contains(entry));
        assertTrue(inverseReport.getDiffList().contains(
            new DiffReportEntry(DiffType.DELETE, entry.getSourcePath())));
      }
    }
  }

};