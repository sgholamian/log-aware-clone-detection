//,temp,TestRegionSplitCalculator.java,337,350,temp,TestRegionSplitCalculator.java,284,299
//,3
public class xxx {
  @Test
  public void testBeginEndMarker() {
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(new SimpleRange(Bytes.toBytes(""), Bytes.toBytes("A")));
    sc.add(new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("B")));
    sc.add(new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("")));

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("Special cases -- empty");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 1, 1, 1, 0);
    assertEquals(":\t[, A]\t\n" + "A:\t[A, B]\t\n" + "B:\t[B, ]\t\n"
        + "null:\t\n", res);
  }

};