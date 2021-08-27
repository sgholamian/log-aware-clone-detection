//,temp,TestLowLevelLrfuCachePolicy.java,246,272,temp,TestLowLevelLrfuCachePolicy.java,144,173
//,3
public class xxx {
  @Test
  public void testDeadlockResolution() {
    int heapSize = 4;
    LOG.info("Testing deadlock resolution");
    ArrayList<LlapDataBuffer> inserted = new ArrayList<LlapDataBuffer>(heapSize);
    EvictionTracker et = new EvictionTracker();
    Configuration conf = new Configuration();
    conf.setInt(HiveConf.ConfVars.LLAP_LRFU_BP_WRAPPER_SIZE.varname, 1);
    LowLevelLrfuCachePolicy lrfu = new LowLevelLrfuCachePolicy(1, heapSize, conf);
    LowLevelCacheMemoryManager mm = new LowLevelCacheMemoryManager(heapSize, lrfu,
        LlapDaemonCacheMetrics.create("test", "1"));
    lrfu.setEvictionListener(et);
    for (int i = 0; i < heapSize; ++i) {
      LlapDataBuffer buffer = LowLevelCacheImpl.allocateFake();
      assertTrue(cache(mm, lrfu, et, buffer));
      inserted.add(buffer);
    }
    // Lock the lowest priority buffer; try to evict - we'll evict some other buffer.
    LlapDataBuffer locked = inserted.get(0);
    lock(lrfu, locked);
    mm.reserveMemory(1, false, null);
    LlapDataBuffer evicted = et.evicted.get(0);
    assertNotNull(evicted);
    assertTrue(evicted.isInvalid());
    assertNotSame(locked, evicted);
    unlock(lrfu, locked);
  }

};