//,temp,sample_3966.java,2,10,temp,sample_3963.java,2,12
//,3
public class xxx {
public void testSplitCalculatorFloor() {
SimpleRange a = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("C"));
SimpleRange b = new SimpleRange(Bytes.toBytes("A"), Bytes.toBytes("B"));
RegionSplitCalculator<SimpleRange> sc = new RegionSplitCalculator<>(cmp);
sc.add(a);
sc.add(b);
Multimap<byte[], SimpleRange> regions = sc.calcCoverage();


log.info("ac and ab overlap in the beginning");
}

};