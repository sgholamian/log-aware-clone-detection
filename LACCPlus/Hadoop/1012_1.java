//,temp,TestLightWeightLinkedSet.java,406,419,temp,TestLightWeightHashSet.java,72,88
//,3
public class xxx {
  @Test(timeout=60000)
  public void testBookmarkSetToHeadOnAddToEmpty() {
    LOG.info("Test bookmark is set after adding to previously empty set.");
    Iterator<Integer> it = set.getBookmark();
    assertFalse(it.hasNext());
    set.add(list.get(0));
    set.add(list.get(1));

    it = set.getBookmark();
    assertTrue(it.hasNext());
    assertEquals(it.next(), list.get(0));
    assertEquals(it.next(), list.get(1));
    assertFalse(it.hasNext());
  }

};