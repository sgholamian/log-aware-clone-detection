//,temp,TestRLESparseResourceAllocation.java,115,151,temp,TestRLESparseResourceAllocation.java,40,75
//,3
public class xxx {
  @Test
  public void testBlocks() {
    ResourceCalculator resCalc = new DefaultResourceCalculator();
    Resource minAlloc = Resource.newInstance(1, 1);

    RLESparseResourceAllocation rleSparseVector =
        new RLESparseResourceAllocation(resCalc, minAlloc);
    int[] alloc = { 10, 10, 10, 10, 10, 10 };
    int start = 100;
    Set<Entry<ReservationInterval, Resource>> inputs =
        generateAllocation(start, alloc, false).entrySet();
    for (Entry<ReservationInterval, Resource> ip : inputs) {
      rleSparseVector.addInterval(ip.getKey(), ip.getValue());
    }
    LOG.info(rleSparseVector.toString());
    Assert.assertFalse(rleSparseVector.isEmpty());
    Assert.assertEquals(Resource.newInstance(0, 0),
        rleSparseVector.getCapacityAtTime(99));
    Assert.assertEquals(Resource.newInstance(0, 0),
        rleSparseVector.getCapacityAtTime(start + alloc.length + 1));
    for (int i = 0; i < alloc.length; i++) {
      Assert.assertEquals(Resource.newInstance(1024 * (alloc[i]), (alloc[i])),
          rleSparseVector.getCapacityAtTime(start + i));
    }
    Assert.assertEquals(Resource.newInstance(0, 0),
        rleSparseVector.getCapacityAtTime(start + alloc.length + 2));
    for (Entry<ReservationInterval, Resource> ip : inputs) {
      rleSparseVector.removeInterval(ip.getKey(), ip.getValue());
    }
    LOG.info(rleSparseVector.toString());
    for (int i = 0; i < alloc.length; i++) {
      Assert.assertEquals(Resource.newInstance(0, 0),
          rleSparseVector.getCapacityAtTime(start + i));
    }
    Assert.assertTrue(rleSparseVector.isEmpty());
  }

};