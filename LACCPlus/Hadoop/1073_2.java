//,temp,TestBlockReaderFactory.java,206,297,temp,TestBlockReaderFactory.java,137,197
//,3
public class xxx {
  @Test(timeout=60000)
  public void testMultipleWaitersOnShortCircuitCache()
      throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicBoolean creationIsBlocked = new AtomicBoolean(true);
    final AtomicBoolean testFailed = new AtomicBoolean(false);
    DFSInputStream.tcpReadsDisabledForTesting = true;
    BlockReaderFactory.createShortCircuitReplicaInfoCallback =
      new ShortCircuitCache.ShortCircuitReplicaCreator() {
        @Override
        public ShortCircuitReplicaInfo createShortCircuitReplicaInfo() {
          Uninterruptibles.awaitUninterruptibly(latch);
          if (!creationIsBlocked.compareAndSet(true, false)) {
            Assert.fail("there were multiple calls to "
                + "createShortCircuitReplicaInfo.  Only one was expected.");
          }
          return null;
        }
      };
    TemporarySocketDirectory sockDir = new TemporarySocketDirectory();
    Configuration conf = createShortCircuitConf(
        "testMultipleWaitersOnShortCircuitCache", sockDir);
    MiniDFSCluster cluster =
        new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
    cluster.waitActive();
    final DistributedFileSystem dfs = cluster.getFileSystem();
    final String TEST_FILE = "/test_file";
    final int TEST_FILE_LEN = 4000;
    final int SEED = 0xFADED;
    final int NUM_THREADS = 10;
    DFSTestUtil.createFile(dfs, new Path(TEST_FILE), TEST_FILE_LEN,
        (short)1, SEED);
    Runnable readerRunnable = new Runnable() {
      @Override
      public void run() {
        try {
          byte contents[] = DFSTestUtil.readFileBuffer(dfs, new Path(TEST_FILE));
          Assert.assertFalse(creationIsBlocked.get());
          byte expected[] = DFSTestUtil.
              calculateFileContentsFromSeed(SEED, TEST_FILE_LEN);
          Assert.assertTrue(Arrays.equals(contents, expected));
        } catch (Throwable e) {
          LOG.error("readerRunnable error", e);
          testFailed.set(true);
        }
      }
    };
    Thread threads[] = new Thread[NUM_THREADS];
    for (int i = 0; i < NUM_THREADS; i++) {
      threads[i] = new Thread(readerRunnable);
      threads[i].start();
    }
    Thread.sleep(500);
    latch.countDown();
    for (int i = 0; i < NUM_THREADS; i++) {
      Uninterruptibles.joinUninterruptibly(threads[i]);
    }
    cluster.shutdown();
    sockDir.close();
    Assert.assertFalse(testFailed.get());
  }

};