//,temp,TestRegionSplitCalculator.java,238,252,temp,TestRegionSplitCalculator.java,130,145
//,3
public class xxx {
  @Test
  public void testSplitCalculator() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("B"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("C"));
    SimpleRange c = new SimpleRange(Bytes.toBytes("C"), Bytes.toBytes("D"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);
    sc.add(c);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("Standard");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 1, 1, 1, 0);
    assertEquals("A:\t[A, B]\t\n" + "B:\t[B, C]\t\n" + "C:\t[C, D]\t\nD:\t\n", res);
  }

};