//,temp,TestLightWeightHashSet.java,193,217,temp,TestLightWeightHashSet.java,172,191
//,3
public class xxx {
  @Test
  public void testPollAll() {
    LOG.info("Test poll all");
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    // remove all elements by polling
    List<Integer> poll = set.pollAll();
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());

    // the deleted elements should not be there
    for (int i = 0; i < NUM; i++) {
      assertFalse(set.contains(list.get(i)));
    }

    // we should get all original items
    for (Integer i : poll) {
      assertTrue(list.contains(i));
    }

    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());
    LOG.info("Test poll all - DONE");
  }

};