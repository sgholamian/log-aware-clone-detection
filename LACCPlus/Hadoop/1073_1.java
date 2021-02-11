//,temp,TestBlockReaderFactory.java,206,297,temp,TestBlockReaderFactory.java,137,197
//,3
public class xxx {
  @Test(timeout=60000)
  public void testShortCircuitCacheTemporaryFailure()
      throws Exception {
    BlockReaderTestUtil.enableBlockReaderFactoryTracing();
    final AtomicBoolean replicaCreationShouldFail = new AtomicBoolean(true);
    final AtomicBoolean testFailed = new AtomicBoolean(false);
    DFSInputStream.tcpReadsDisabledForTesting = true;
    BlockReaderFactory.createShortCircuitReplicaInfoCallback =
      new ShortCircuitCache.ShortCircuitReplicaCreator() {
        @Override
        public ShortCircuitReplicaInfo createShortCircuitReplicaInfo() {
          if (replicaCreationShouldFail.get()) {
            // Insert a short delay to increase the chance that one client
            // thread waits for the other client thread's failure via
            // a condition variable.
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            return new ShortCircuitReplicaInfo();
          }
          return null;
        }
      };
    TemporarySocketDirectory sockDir = new TemporarySocketDirectory();
    Configuration conf = createShortCircuitConf(
        "testShortCircuitCacheTemporaryFailure", sockDir);
    final MiniDFSCluster cluster =
        new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
    cluster.waitActive();
    final DistributedFileSystem dfs = cluster.getFileSystem();
    final String TEST_FILE = "/test_file";
    final int TEST_FILE_LEN = 4000;
    final int NUM_THREADS = 2;
    final int SEED = 0xFADED;
    final CountDownLatch gotFailureLatch = new CountDownLatch(NUM_THREADS);
    final CountDownLatch shouldRetryLatch = new CountDownLatch(1);
    DFSTestUtil.createFile(dfs, new Path(TEST_FILE), TEST_FILE_LEN,
        (short)1, SEED);
    Runnable readerRunnable = new Runnable() {
      @Override
      public void run() {
        try {
          // First time should fail.
          List<LocatedBlock> locatedBlocks = 
              cluster.getNameNode().getRpcServer().getBlockLocations(
              TEST_FILE, 0, TEST_FILE_LEN).getLocatedBlocks();
          LocatedBlock lblock = locatedBlocks.get(0); // first block
          BlockReader blockReader = null;
          try {
            blockReader = BlockReaderTestUtil.
                getBlockReader(cluster, lblock, 0, TEST_FILE_LEN);
            Assert.fail("expected getBlockReader to fail the first time.");
          } catch (Throwable t) { 
            Assert.assertTrue("expected to see 'TCP reads were disabled " +
                "for testing' in exception " + t, t.getMessage().contains(
                "TCP reads were disabled for testing"));
          } finally {
            if (blockReader != null) blockReader.close(); // keep findbugs happy
          }
          gotFailureLatch.countDown();
          shouldRetryLatch.await();

          // Second time should succeed.
          try {
            blockReader = BlockReaderTestUtil.
                getBlockReader(cluster, lblock, 0, TEST_FILE_LEN);
          } catch (Throwable t) { 
            LOG.error("error trying to retrieve a block reader " +
                "the second time.", t);
            throw t;
          } finally {
            if (blockReader != null) blockReader.close();
          }
        } catch (Throwable t) {
          LOG.error("getBlockReader failure", t);
          testFailed.set(true);
        }
      }
    };
    Thread threads[] = new Thread[NUM_THREADS];
    for (int i = 0; i < NUM_THREADS; i++) {
      threads[i] = new Thread(readerRunnable);
      threads[i].start();
    }
    gotFailureLatch.await();
    replicaCreationShouldFail.set(false);
    shouldRetryLatch.countDown();
    for (int i = 0; i < NUM_THREADS; i++) {
      Uninterruptibles.joinUninterruptibly(threads[i]);
    }
    cluster.shutdown();
    sockDir.close();
    Assert.assertFalse(testFailed.get());
  }

};