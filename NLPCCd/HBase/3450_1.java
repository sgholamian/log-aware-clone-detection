//,temp,sample_3966.java,2,10,temp,sample_3963.java,2,12
//,3
public class xxx {
public void testSplitCalculatorBackwards() {
SimpleRange a = new SimpleRange(Bytes.toBytes("C"), Bytes.toBytes("A"));
RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
sc.add(a);
Multimap<byte[], SimpleRange> regions = sc.calcCoverage();


log.info("ca is backwards");
}

};