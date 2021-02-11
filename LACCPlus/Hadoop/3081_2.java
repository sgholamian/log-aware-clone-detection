//,temp,TestLightWeightLinkedSet.java,280,288,temp,TestLightWeightLinkedSet.java,206,213
//,3
public class xxx {
  @Test
  public void testPollOneElement() {
    LOG.info("Test poll one element");
    set.add(list.get(0));
    assertEquals(list.get(0), set.pollFirst());
    assertNull(set.pollFirst());
    LOG.info("Test poll one element - DONE");
  }

};