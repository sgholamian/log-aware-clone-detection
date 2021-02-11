//,temp,TestDFSNetworkTopologyPerformance.java,419,466,temp,TestDFSNetworkTopologyPerformance.java,346,409
//,3
public class xxx {
  @Test
  public void testPercentageStorageTypeWithMixedTopology() throws Exception {
    double percentage = 0.9;
    for (int i = 0; i < NODE_NUM; i++) {
      if (coinFlip(percentage)) {
        types[i] = StorageType.ARCHIVE;
      } else {
        types[i] = StorageType.DISK;
      }
    }
    addNodeByTypes(types);

    // wait a bit for things to become stable
    Thread.sleep(1000);
    printMemUsage("before test1");

    totalStart = System.nanoTime();
    totalTrials = 0;
    for (int i = 0; i < OP_NUM; i++) {
      // mimic the behavior of current code:
      // 1. chooseRandom on NetworkTopology
      // 2. if it satisfies, we are good, do the next operation
      // 3. if not, chooseRandom on DFSNetworkTopology
      localStart = System.nanoTime();

      totalTrials += 1;
      node = cluster.chooseRandom("", excluded);
      assertNotNull(node);
      if (!isType(node, StorageType.ARCHIVE)) {
        totalTrials += 1;
        excluded.add(node);
        node = dfscluster.chooseRandomWithStorageType("", excluded,
            StorageType.ARCHIVE);
      }
      assertTrue(isType(node, StorageType.ARCHIVE));

      excluded.clear();
      localEnd = System.nanoTime();
      records[i] = localEnd - localStart;
    }
    totalEnd = System.nanoTime();
    totalMs = (totalEnd - totalStart)/NS_TO_MS;
    LOG.info("total time: {} avg time: {} avg trials: {}",
        totalMs, totalMs / OP_NUM, (float)totalTrials / OP_NUM);
    // wait a bit for things to become stable
    Thread.sleep(1000);
    printMemUsage("test StorageType with mixed topology.");
  }

};