//,temp,TestLightWeightLinkedSet.java,58,73,temp,TestLightWeightHashSet.java,72,88
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

    // poll should return nothing
    assertNull(set.pollFirst());
    assertEquals(0, set.pollAll().size());
    assertEquals(0, set.pollN(10).size());

    LOG.info("Test empty - DONE");
  }

};