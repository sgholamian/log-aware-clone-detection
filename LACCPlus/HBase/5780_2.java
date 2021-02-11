//,temp,TestRegionSplitCalculator.java,269,282,temp,TestRegionSplitCalculator.java,202,218
//,3
public class xxx {
  @Test
  public void testSplitCalculatorOverEndpoint() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("B"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("C"));
    SimpleRange c = new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("D"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);
    sc.add(c);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("AB, BD covers BC");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 1, 2, 1, 0);
    assertEquals("A:\t[A, B]\t\n" + "B:\t[B, C]\t[B, D]\t\n"
        + "C:\t[B, D]\t\n" + "D:\t\n", res);
  }

};