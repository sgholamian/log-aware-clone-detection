//,temp,TestLightWeightLinkedSet.java,185,204,temp,TestLightWeightLinkedSet.java,155,183
//,3
public class xxx {
  @Test
  public void testRemoveAll() {
    LOG.info("Test remove all");
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    for (int i = 0; i < NUM; i++) {
      assertTrue(set.remove(list.get(i)));
    }
    // the deleted elements should not be there
    for (int i = 0; i < NUM; i++) {
      assertFalse(set.contains(list.get(i)));
    }

    // iterator should not have next
    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());
    assertTrue(set.isEmpty());
    LOG.info("Test remove all - DONE");
  }

};