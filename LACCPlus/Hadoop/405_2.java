//,temp,TestLightWeightLinkedSet.java,406,419,temp,TestLightWeightLinkedSet.java,392,404
//,3
public class xxx {
  @Test(timeout=60000)
  public void testBookmarkAdvancesOnRemoveOfSameElement() {
    LOG.info("Test that the bookmark advances if we remove its element.");
    assertTrue(set.add(list.get(0)));
    assertTrue(set.add(list.get(1)));
    assertTrue(set.add(list.get(2)));

    Iterator<Integer> it = set.getBookmark();
    assertEquals(it.next(), list.get(0));
    set.remove(list.get(1));
    it = set.getBookmark();
    assertEquals(it.next(), list.get(2));
  }

};