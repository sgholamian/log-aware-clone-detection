//,temp,TestLowLevelLrfuCachePolicy.java,246,272,temp,TestLowLevelLrfuCachePolicy.java,144,173
//,3
public class xxx {
  @Test
  public void testLfuExtreme() {
    int heapSize = 4;
    LOG.info("Testing lambda 0 (LFU)");
    Random rdm = new Random(1234);
    Configuration conf = new Configuration();
    ArrayList<LlapDataBuffer> inserted = new ArrayList<LlapDataBuffer>(heapSize);
    conf.setFloat(HiveConf.ConfVars.LLAP_LRFU_LAMBDA.varname, 0.0f);
    conf.setInt(HiveConf.ConfVars.LLAP_LRFU_BP_WRAPPER_SIZE.varname, 1);
    EvictionTracker et = new EvictionTracker();
    LowLevelLrfuCachePolicy lfu = new LowLevelLrfuCachePolicy(1, heapSize, conf);
    LowLevelCacheMemoryManager mm = new LowLevelCacheMemoryManager(heapSize, lfu,
        LlapDaemonCacheMetrics.create("test", "1"));
    lfu.setEvictionListener(et);
    for (int i = 0; i < heapSize; ++i) {
      LlapDataBuffer buffer = LowLevelCacheImpl.allocateFake();
      assertTrue(cache(mm, lfu, et, buffer));
      inserted.add(buffer);
    }
    Collections.shuffle(inserted, rdm);
    // LFU extreme, order of accesses should be ignored, only frequency matters.
    // We touch first elements later, but do it less times, so they will be evicted first.
    for (int i = inserted.size() - 1; i >= 0; --i) {
      for (int j = 0; j < i + 1; ++j) {
        lfu.notifyLock(inserted.get(i));
        lfu.notifyUnlock(inserted.get(i));
      }
    }
    verifyOrder(mm, lfu, et, inserted, null);
  }

};