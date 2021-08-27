//,temp,TestBuddyAllocatorForceEvict.java,433,454,temp,TestBuddyAllocator.java,266,283
//,3
public class xxx {
  private void allocateAndUseBuffer(BuddyAllocator a, MemoryBuffer[][] allocs,
      long[][] testValues, int allocCount, int index, int sizeLog2) throws Exception {
    allocs[index] = new MemoryBuffer[allocCount];
    testValues[index] = new long[allocCount];
    int size = (1 << sizeLog2) - 1;
    try {
      a.allocateMultiple(allocs[index], size);
    } catch (AllocatorOutOfMemoryException ex) {
      LOG.error("Failed to allocate " + allocCount + " of " + size + "; " + a.testDump());
      throw ex;
    }
    // LOG.info("Allocated " + allocCount + " of " + size + "; " + a.debugDump());
    for (int j = 0; j < allocCount; ++j) {
      MemoryBuffer mem = allocs[index][j];
      long testValue = testValues[index][j] = rdm.nextLong();
      putTestValue(mem, testValue);
    }
  }

};