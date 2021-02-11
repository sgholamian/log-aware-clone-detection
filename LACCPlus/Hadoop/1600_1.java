//,temp,TestLightWeightLinkedSet.java,320,352,temp,TestLightWeightLinkedSet.java,127,153
//,3
public class xxx {
  @Test
  public void testClear() {
    LOG.info("Test clear");
    // use addAll
    set.addAll(list);
    assertEquals(NUM, set.size());
    assertFalse(set.isEmpty());

    // Advance the bookmark.
    Iterator<Integer> bkmrkIt = set.getBookmark();
    for (int i=0; i<set.size()/2+1; i++) {
      bkmrkIt.next();
    }
    assertTrue(bkmrkIt.hasNext());

    // clear the set
    set.clear();
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());
    bkmrkIt = set.getBookmark();
    assertFalse(bkmrkIt.hasNext());

    // poll should return an empty list
    assertEquals(0, set.pollAll().size());
    assertEquals(0, set.pollN(10).size());
    assertNull(set.pollFirst());

    // iterator should be empty
    Iterator<Integer> iter = set.iterator();
    assertFalse(iter.hasNext());

    LOG.info("Test clear - DONE");
  }

};