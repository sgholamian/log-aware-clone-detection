//,temp,TestUserScanQueryMatcher.java,120,161,temp,TestUserScanQueryMatcher.java,75,118
//,3
public class xxx {
  @Test
  public void testMatchExplicitColumns() throws IOException {
    // Moving up from the Tracker by using Gets and List<KeyValue> instead
    // of just byte []

    // Expected result
    List<MatchCode> expected = new ArrayList<>(6);
    expected.add(ScanQueryMatcher.MatchCode.SEEK_NEXT_COL);
    expected.add(ScanQueryMatcher.MatchCode.INCLUDE_AND_SEEK_NEXT_COL);
    expected.add(ScanQueryMatcher.MatchCode.SEEK_NEXT_COL);
    expected.add(ScanQueryMatcher.MatchCode.INCLUDE_AND_SEEK_NEXT_COL);
    expected.add(ScanQueryMatcher.MatchCode.INCLUDE_AND_SEEK_NEXT_ROW);
    expected.add(ScanQueryMatcher.MatchCode.DONE);

    long now = EnvironmentEdgeManager.currentTime();
    // 2,4,5
    UserScanQueryMatcher qm = UserScanQueryMatcher.create(
      scan, new ScanInfo(this.conf, fam2, 0, 1, ttl, KeepDeletedCells.FALSE,
          HConstants.DEFAULT_BLOCKSIZE, 0, rowComparator, false),
      get.getFamilyMap().get(fam2), now - ttl, now, null);

    List<KeyValue> memstore = new ArrayList<>(6);
    memstore.add(new KeyValue(row1, fam2, col1, 1, data));
    memstore.add(new KeyValue(row1, fam2, col2, 1, data));
    memstore.add(new KeyValue(row1, fam2, col3, 1, data));
    memstore.add(new KeyValue(row1, fam2, col4, 1, data));
    memstore.add(new KeyValue(row1, fam2, col5, 1, data));

    memstore.add(new KeyValue(row2, fam1, col1, data));

    List<ScanQueryMatcher.MatchCode> actual = new ArrayList<>(memstore.size());
    KeyValue k = memstore.get(0);
    qm.setToNewRow(k);

    for (KeyValue kv : memstore) {
      actual.add(qm.match(kv));
    }

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      LOG.debug("expected " + expected.get(i) + ", actual " + actual.get(i));
      assertEquals(expected.get(i), actual.get(i));
    }
  }

};