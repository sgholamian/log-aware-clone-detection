//,temp,TestLightWeightLinkedSet.java,259,278,temp,TestLightWeightLinkedSet.java,93,125
//,3
public class xxx {
  @Test
  public void testPollAll() {
    LOG.info("Test poll all");
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    // remove all elements by polling
    while (set.pollFirst() != null);
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());

    // the deleted elements should not be there
    for (int i = 0; i < NUM; i++) {
      assertFalse(set.contains(list.get(i)));
    }

    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());
    LOG.info("Test poll all - DONE");
  }

};