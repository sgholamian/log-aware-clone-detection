//,temp,TestLightWeightLinkedSet.java,290,318,temp,TestLightWeightHashSet.java,219,258
//,3
public class xxx {
  @Test
  public void testPollNMulti() {
    LOG.info("Test pollN multi");

    // use addAll
    set.addAll(list);

    // poll existing elements
    List<Integer> l = set.pollN(10);
    assertEquals(10, l.size());

    for (int i = 0; i < 10; i++) {
      assertEquals(list.get(i), l.get(i));
    }

    // poll more elements than present
    l = set.pollN(1000);
    assertEquals(NUM - 10, l.size());

    // check the order
    for (int i = 10; i < NUM; i++) {
      assertEquals(list.get(i), l.get(i - 10));
    }
    // set is empty
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    LOG.info("Test pollN multi - DONE");
  }

};