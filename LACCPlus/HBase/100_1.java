//,temp,TestRegionSplitCalculator.java,301,312,temp,TestRegionSplitCalculator.java,238,252
//,3
public class xxx {
  @Test
  public void testSplitCalculatorBackwards() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("C"), Bytes.toBytes("A"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("CA is backwards");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions); // expect nothing
    assertEquals("", res);
  }

};