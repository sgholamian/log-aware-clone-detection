//,temp,TestBlockReaderFactory.java,435,533,temp,TestBlockReaderFactory.java,206,297
//,3
public class xxx {
  @Test(timeout=120000)
  public void testPurgingClosedReplicas() throws Exception {
    BlockReaderTestUtil.enableBlockReaderFactoryTracing();
    final AtomicInteger replicasCreated = new AtomicInteger(0);
    final AtomicBoolean testFailed = new AtomicBoolean(false);
    DFSInputStream.tcpReadsDisabledForTesting = true;
    BlockReaderFactory.createShortCircuitReplicaInfoCallback =
        new ShortCircuitCache.ShortCircuitReplicaCreator() {
          @Override
          public ShortCircuitReplicaInfo createShortCircuitReplicaInfo() {
            replicasCreated.incrementAndGet();
            return null;
          }
        };
    TemporarySocketDirectory sockDir = new TemporarySocketDirectory();
    Configuration conf = createShortCircuitConf(
        "testPurgingClosedReplicas", sockDir);
    final MiniDFSCluster cluster =
        new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
    cluster.waitActive();
    final DistributedFileSystem dfs = cluster.getFileSystem();
    final String TEST_FILE = "/test_file";
    final int TEST_FILE_LEN = 4095;
    final int SEED = 0xFADE0;
    final DistributedFileSystem fs =
        (DistributedFileSystem)FileSystem.get(cluster.getURI(0), conf);
    DFSTestUtil.createFile(fs, new Path(TEST_FILE), TEST_FILE_LEN,
        (short)1, SEED);

    final Semaphore sem = new Semaphore(0);
    final List<LocatedBlock> locatedBlocks =
        cluster.getNameNode().getRpcServer().getBlockLocations(
            TEST_FILE, 0, TEST_FILE_LEN).getLocatedBlocks();
    final LocatedBlock lblock = locatedBlocks.get(0); // first block
    final byte[] buf = new byte[TEST_FILE_LEN];
    Runnable readerRunnable = new Runnable() {
      @Override
      public void run() {
        try {
          while (true) {
            BlockReader blockReader = null;
            try {
              blockReader = BlockReaderTestUtil.
                  getBlockReader(cluster, lblock, 0, TEST_FILE_LEN);
              sem.release();
              try {
                blockReader.readAll(buf, 0, TEST_FILE_LEN);
              } finally {
                sem.acquireUninterruptibly();
              }
            } catch (ClosedByInterruptException e) {
              LOG.info("got the expected ClosedByInterruptException", e);
              sem.release();
              break;
            } finally {
              if (blockReader != null) blockReader.close();
            }
            LOG.info("read another " + TEST_FILE_LEN + " bytes.");
          }
        } catch (Throwable t) {
          LOG.error("getBlockReader failure", t);
          testFailed.set(true);
          sem.release();
        }
      }
    };
    Thread thread = new Thread(readerRunnable);
    thread.start();

    // While the thread is reading, send it interrupts.
    // These should trigger a ClosedChannelException.
    while (thread.isAlive()) {
      sem.acquireUninterruptibly();
      thread.interrupt();
      sem.release();
    }
    Assert.assertFalse(testFailed.get());

    // We should be able to read from the file without
    // getting a ClosedChannelException.
    BlockReader blockReader = null;
    try {
      blockReader = BlockReaderTestUtil.
          getBlockReader(cluster, lblock, 0, TEST_FILE_LEN);
      blockReader.readFully(buf, 0, TEST_FILE_LEN);
    } finally {
      if (blockReader != null) blockReader.close();
    }
    byte expected[] = DFSTestUtil.
        calculateFileContentsFromSeed(SEED, TEST_FILE_LEN);
    Assert.assertTrue(Arrays.equals(buf, expected));

    // Another ShortCircuitReplica object should have been created.
    Assert.assertEquals(2, replicasCreated.get());

    dfs.close();
    cluster.shutdown();
    sockDir.close();
  }

};