//,temp,TestLightWeightLinkedSet.java,155,183,temp,TestLightWeightHashSet.java,172,191
//,3
public class xxx {
  @Test
  public void testRemoveMulti() {
    LOG.info("Test remove multi");
    for (Integer i : list) {
      assertTrue(set.add(i));
    }
    for (int i = 0; i < NUM / 2; i++) {
      assertTrue(set.remove(list.get(i)));
    }

    // the deleted elements should not be there
    for (int i = 0; i < NUM / 2; i++) {
      assertFalse(set.contains(list.get(i)));
    }

    // the rest should be there
    for (int i = NUM / 2; i < NUM; i++) {
      assertTrue(set.contains(list.get(i)));
    }

    Iterator<Integer> iter = set.iterator();
    // the remaining elements should be in order
    int num = NUM / 2;
    while (iter.hasNext()) {
      assertEquals(list.get(num++), iter.next());
    }
    assertEquals(num, NUM);
    LOG.info("Test remove multi - DONE");
  }

};