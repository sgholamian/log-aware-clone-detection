//,temp,TestRegionSplitCalculator.java,301,312,temp,TestRegionSplitCalculator.java,254,267
//,3
public class xxx {
  @Test
  public void testSplitCalculatorFloor() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("B"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("AC and AB overlap in the beginning");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 2, 1, 0);
    assertEquals("A:\t[A, B]\t[A, C]\t\n" + "B:\t[A, C]\t\n" + "C:\t\n", res);
  }

};