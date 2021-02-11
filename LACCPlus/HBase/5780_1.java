//,temp,TestRegionSplitCalculator.java,269,282,temp,TestRegionSplitCalculator.java,202,218
//,3
public class xxx {
  @Test
  public void testSplitCalculatorCeil() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("C"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("AC and BC overlap in the end");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 1, 2, 0);
    assertEquals("A:\t[A, C]\t\n" + "B:\t[A, C]\t[B, C]\t\n" + "C:\t\n", res);
  }

};