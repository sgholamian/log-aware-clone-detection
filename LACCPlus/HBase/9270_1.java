//,temp,TestRegionSplitCalculator.java,238,252,temp,TestRegionSplitCalculator.java,130,145
//,3
public class xxx {
  @Test
  public void testSplitCalculatorOverreach() {
    SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));
    SimpleRange b = new SimpleRange(Bytes.toBytes("B"), Bytes.toBytes("D"));
    RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
    sc.add(a);
    sc.add(b);

    Multimap<byte[], SimpleRange> regions = sc.calcCoverage();
    LOG.info("AC and BD overlap but share no start/end keys");
    String res = dump(sc.getSplits(), regions);
    checkDepths(sc.getSplits(), regions, 1, 2, 1, 0);
    assertEquals("A:\t[A, C]\t\n" + "B:\t[A, C]\t[B, D]\t\n"
        + "C:\t[B, D]\t\n" + "D:\t\n", res);
  }

};