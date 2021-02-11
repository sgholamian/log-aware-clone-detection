//,temp,TestPeriodicRLESparseResourceAllocation.java,56,75,temp,TestRLESparseResourceAllocation.java,536,559
//,3
public class xxx {
  @Test
  public void testMaxPeriodicCapacity() {
    long[] timeSteps = { 0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L };
    int[] alloc = { 2, 5, 7, 10, 3, 4, 6, 8 };
    RLESparseResourceAllocation rleSparseVector = ReservationSystemTestUtil
        .generateRLESparseResourceAllocation(alloc, timeSteps);
    LOG.info(rleSparseVector.toString());
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 1),
        Resource.newInstance(10, 10));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 2),
        Resource.newInstance(7, 7));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 3),
        Resource.newInstance(10, 10));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 4),
        Resource.newInstance(3, 3));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 5),
        Resource.newInstance(4, 4));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(0, 5),
        Resource.newInstance(4, 4));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(7, 5),
        Resource.newInstance(8, 8));
    Assert.assertEquals(rleSparseVector.getMaximumPeriodicCapacity(10, 3),
        Resource.newInstance(0, 0));
  }

};