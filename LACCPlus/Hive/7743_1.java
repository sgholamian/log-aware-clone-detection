//,temp,TestBuddyAllocatorForceEvict.java,433,454,temp,TestBuddyAllocator.java,266,283
//,3
public class xxx {
  public static LlapAllocatorBuffer[] allocate(
      BuddyAllocator a, int count, int size, int baseValue, boolean doIncRef) {
    LlapAllocatorBuffer[] allocs = new LlapAllocatorBuffer[count];
    try {
      a.allocateMultiple(allocs, size);
    } catch (AllocatorOutOfMemoryException ex) {
      LOG.error("Failed to allocate " + allocs.length + " of " + size + "; " + a.testDump());
      throw ex;
    }
    for (int i = 0; i < count; ++i) {
      // Make sure buffers are eligible for discard.
      if (doIncRef) {
        int rc = allocs[i].incRef();
        assertTrue(rc > 0);
      }
      TestBuddyAllocator.putTestValue(allocs[i], baseValue + i);
      if (doIncRef) {
        allocs[i].decRef();
      }
    }
    return allocs;
  }

};