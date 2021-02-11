//,temp,sample_1562.java,2,13,temp,sample_1580.java,2,14
//,3
public class xxx {
public void testClear() {
set.addAll(list);
assertEquals(NUM, set.size());
assertFalse(set.isEmpty());
set.clear();
assertEquals(0, set.size());
assertTrue(set.isEmpty());
Iterator<Integer> iter = set.iterator();
assertFalse(iter.hasNext());


log.info("test clear done");
}

};