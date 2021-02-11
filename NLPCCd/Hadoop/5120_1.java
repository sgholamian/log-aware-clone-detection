//,temp,sample_1562.java,2,13,temp,sample_1580.java,2,14
//,3
public class xxx {
public void testOneElementBasic() {
set.add(list.get(0));
assertEquals(1, set.size());
assertFalse(set.isEmpty());
Iterator<Integer> iter = set.iterator();
assertTrue(iter.hasNext());
assertEquals(list.get(0), iter.next());
assertFalse(iter.hasNext());


log.info("test one element basic done");
}

};