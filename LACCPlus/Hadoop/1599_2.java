//,temp,TestLightWeightLinkedSet.java,290,318,temp,TestLightWeightHashSet.java,219,258
//,3
public class xxx {
  @Test
  public void testPollNMulti() {
    LOG.info("Test pollN multi");

    // use addAll
    set.addAll(list);

    // poll zero
    List<Integer> poll = set.pollN(0);
    assertEquals(0, poll.size());
    for (Integer i : list) {
      assertTrue(set.contains(i));
    }

    // poll existing elements (less than size)
    poll = set.pollN(10);
    assertEquals(10, poll.size());

    for (Integer i : poll) {
      // should be in original items
      assertTrue(list.contains(i));
      // should not be in the set anymore
      assertFalse(set.contains(i));
    }

    // poll more elements than present
    poll = set.pollN(1000);
    assertEquals(NUM - 10, poll.size());

    for (Integer i : poll) {
      // should be in original items
      assertTrue(list.contains(i));
    }

    // set is empty
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    LOG.info("Test pollN multi - DONE");
  }

};