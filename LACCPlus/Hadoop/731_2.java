//,temp,TestLightWeightHashSet.java,193,217,temp,TestLightWeightHashSet.java,90,125
//,3
public class xxx {
  @Test
  public void testMultiBasic() {
    LOG.info("Test multi element basic");
    // add once
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    assertEquals(list.size(), set.size());

    // check if the elements are in the set
    for (Integer i : list) {
      assertTrue(set.contains(i));
    }

    // add again - should return false each time
    for (Integer i : list) {
      assertFalse(set.add(i));
    }

    // check again if the elements are there
    for (Integer i : list) {
      assertTrue(set.contains(i));
    }

    Iterator<Integer> iter = set.iterator();
    int num = 0;
    while (iter.hasNext()) {
      Integer next = iter.next();
      assertNotNull(next);
      assertTrue(list.contains(next));
      num++;
    }
    // check the number of element from the iterator
    assertEquals(list.size(), num);
    LOG.info("Test multi element basic - DONE");
  }

};