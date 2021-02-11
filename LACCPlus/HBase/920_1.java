//,temp,TestRegionSplitCalculator.java,284,299,temp,TestRegionSplitCalculator.java,202,218
//,3
public class xxx {
  @Test
  public void testSplitCalculatorEq() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));

    LOG.info(a.tiebreaker + " - " + b.tiebreaker);
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("AC and AC overlap completely");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 2, 0);
    assertEquals("A:\t[A, C]\t[A, C]\t\n" + "C:\t\n", res);
  }

};