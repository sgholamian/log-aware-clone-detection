//,temp,TestLightWeightLinkedSet.java,75,91,temp,TestLightWeightHashSet.java,127,148
//,3
public class xxx {
  @Test
  public void testRemoveOne() {
    LOG.info("Test remove one");
    assertTrue(set.add(list.get(0)));
    assertEquals(1, set.size());

    // remove from the head/tail
    assertTrue(set.remove(list.get(0)));
    assertEquals(0, set.size());

    // check the iterator
    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());

    // add the element back to the set
    assertTrue(set.add(list.get(0)));
    assertEquals(1, set.size());

    iter = set.iterator();
    assertTrue(iter.hasNext());
    LOG.info("Test remove one - DONE");
  }

};