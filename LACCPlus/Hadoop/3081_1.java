//,temp,TestLightWeightLinkedSet.java,280,288,temp,TestLightWeightLinkedSet.java,206,213
//,3
public class xxx {
  @Test
  public void testPollNOne() {
    LOG.info("Test pollN one");
    set.add(list.get(0));
    List<Integer> l = set.pollN(10);
    assertEquals(1, l.size());
    assertEquals(list.get(0), l.get(0));
    LOG.info("Test pollN one - DONE");
  }

};