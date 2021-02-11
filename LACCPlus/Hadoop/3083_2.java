//,temp,TestLightWeightLinkedSet.java,259,278,temp,TestLightWeightHashSet.java,193,218
//,3
public class xxx {
  @Test
  public void testRemoveAllViaIterator() {
    LOG.info("Test remove all via iterator");
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    for (Iterator<Integer> iter = set.iterator(); iter.hasNext(); ) {
      int e = iter.next();
      // element should be there before removing
      assertTrue(set.contains(e));
      iter.remove();
      // element should not be there now
      assertFalse(set.contains(e));
    }

    // the deleted elements should not be there
    for (int i = 0; i < NUM; i++) {
      assertFalse(set.contains(list.get(i)));
    }

    // iterator should not have next
    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());
    assertTrue(set.isEmpty());
    LOG.info("Test remove all via iterator - DONE");
  }

};