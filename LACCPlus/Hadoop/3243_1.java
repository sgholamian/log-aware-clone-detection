//,temp,TestDFSNetworkTopologyPerformance.java,346,409,temp,TestDFSNetworkTopologyPerformance.java,209,270
//,3
public class xxx {
  @Test
  public void testPercentageStorageType() throws Exception {
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
      // mimic the behaviour of current code:
      // 1. chooseRandom on NetworkTopology
      // 2. if it satisfies, we are good, break
      // 3. if not, add to excluded, try again
      localStart = System.nanoTime();
      do {
        totalTrials += 1;
        node = cluster.chooseRandom("", excluded);
        assertNotNull(node);
        if (isType(node, StorageType.ARCHIVE)) {
          break;
        }
        excluded.add(node);
      } while (true);
      excluded.clear();
      localEnd = System.nanoTime();
      records[i] = localEnd - localStart;
    }
    totalEnd = System.nanoTime();
    totalMs = (totalEnd - totalStart) / NS_TO_MS;
    // on average it takes 20 trials
    LOG.info("total time: {} avg time: {} avg trials: {}",
        totalMs, totalMs / OP_NUM, (float)totalTrials / OP_NUM);
    // wait a bit for things to become stable
    Thread.sleep(1000);
    printMemUsage("after test1 before test2");

    totalStart = System.nanoTime();
    for (int i = 0; i < OP_NUM; i++) {
      localStart = System.nanoTime();
      node = dfscluster.chooseRandomWithStorageType("", excluded,
          StorageType.ARCHIVE);
      assertNotNull(node);
      // with dfs cluster, the returned is always already the required type;
      // add assertion mainly to make a more fair comparison
      assertTrue(isType(node, StorageType.ARCHIVE));
      localEnd = System.nanoTime();
      records[i] = localEnd - localStart;
    }
    totalEnd = System.nanoTime();
    totalMs = (totalEnd - totalStart) / NS_TO_MS;
    LOG.info("total time: {} avg time: {}", totalMs, totalMs / OP_NUM);

    printMemUsage("after test2");
  }

};