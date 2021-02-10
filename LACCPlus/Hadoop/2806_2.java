//,temp,TestLightWeightHashSet.java,172,191,temp,TestLightWeightHashSet.java,150,170
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
    LOG.info("Test remove multi - DONE");
  }

};