//,temp,TestLightWeightLinkedSet.java,75,91,temp,TestLightWeightHashSet.java,61,70
//,3
public class xxx {
  @Test
  public void testEmptyBasic() {
    LOG.info("Test empty basic");
    Iterator<Integer> iter = set.iterator();
    // iterator should not have next
    assertFalse(iter.hasNext());
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());
    LOG.info("Test empty - DONE");
  }

};