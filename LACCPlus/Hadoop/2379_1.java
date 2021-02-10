//,temp,TestPeriodicRLESparseResourceAllocation.java,56,75,temp,TestRLESparseResourceAllocation.java,536,559
//,3
public class xxx {
  @Test
  public void testMaxPeriodicCapacity() {
    int[] alloc = { 2, 5, 7, 10, 3, 4, 6, 8 };
    long[] timeSteps = { 0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L };
    RLESparseResourceAllocation rleSparseVector = ReservationSystemTestUtil
        .generateRLESparseResourceAllocation(alloc, timeSteps);
    PeriodicRLESparseResourceAllocation periodicVector =
        new PeriodicRLESparseResourceAllocation(rleSparseVector, 8L);
    LOG.info(periodicVector.toString());
    Assert.assertEquals(periodicVector.getMaximumPeriodicCapacity(0, 1),
        Resource.newInstance(10, 10));
    Assert.assertEquals(periodicVector.getMaximumPeriodicCapacity(8, 2),
        Resource.newInstance(7, 7));
    Assert.assertEquals(periodicVector.getMaximumPeriodicCapacity(16, 3),
        Resource.newInstance(10, 10));
    Assert.assertEquals(periodicVector.getMaximumPeriodicCapacity(17, 4),
        Resource.newInstance(5, 5));
    Assert.assertEquals(periodicVector.getMaximumPeriodicCapacity(32, 5),
        Resource.newInstance(4, 4));
  }

};