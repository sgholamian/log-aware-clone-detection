//,temp,TestLightWeightHashSet.java,319,337,temp,TestLightWeightHashSet.java,72,88
//,3
public class xxx {
  @Test
  public void testOneElementBasic() {
    LOG.info("Test one element basic");
    set.add(list.get(0));
    // set should be non-empty
    assertEquals(1, set.size());
    assertFalse(set.isEmpty());

    // iterator should have next
    Iterator<Integer> iter = set.iterator();
    assertTrue(iter.hasNext());

    // iterator should not have next
    assertEquals(list.get(0), iter.next());
    assertFalse(iter.hasNext());
    LOG.info("Test one element basic - DONE");
  }

};