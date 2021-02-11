//,temp,TestLightWeightLinkedSet.java,58,73,temp,TestLightWeightHashSet.java,319,337
//,3
public class xxx {
  @Test
  public void testClear() {
    LOG.info("Test clear");
    // use addAll
    set.addAll(list);
    assertEquals(NUM, set.size());
    assertFalse(set.isEmpty());

    // clear the set
    set.clear();
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());

    // iterator should be empty
    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());

    LOG.info("Test clear - DONE");
  }

};