//,temp,TestFileConcurrentReader.java,324,416,temp,TestFileConcurrentReader.java,203,276
//,3
public class xxx {
  private void runTestUnfinishedBlockCRCError(
    final boolean transferToAllowed,
    final SyncType syncType,
    final int writeSize,
    Configuration conf
  ) throws IOException {
    conf.setBoolean(DFSConfigKeys.DFS_DATANODE_TRANSFERTO_ALLOWED_KEY,
        transferToAllowed);
    init(conf);

    final Path file = new Path("/block-being-written-to");
    final int numWrites = 2000;
    final AtomicBoolean writerDone = new AtomicBoolean(false);
    final AtomicBoolean writerStarted = new AtomicBoolean(false);
    final AtomicBoolean error = new AtomicBoolean(false);

    final Thread writer = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          FSDataOutputStream outputStream = fileSystem.create(file);
          if (syncType == SyncType.APPEND) {
            outputStream.close();
            outputStream = fileSystem.append(file);
          }
          try {
            for (int i = 0; !error.get() && i < numWrites; i++) {
              final byte[] writeBuf =
                  DFSTestUtil.generateSequentialBytes(i * writeSize, writeSize);
              outputStream.write(writeBuf);
              if (syncType == SyncType.SYNC) {
                outputStream.hflush();
              }
              writerStarted.set(true);
            }
          } catch (IOException e) {
            error.set(true);
            LOG.error("error writing to file", e);
          } finally {
            outputStream.close();
          }
          writerDone.set(true);
        } catch (Exception e) {
          LOG.error("error in writer", e);

          throw new RuntimeException(e);
        }
      }
    });
    Thread tailer = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          long startPos = 0;
          while (!writerDone.get() && !error.get()) {
            if (writerStarted.get()) {
              try {
                startPos = tailFile(file, startPos);
              } catch (IOException e) {
                LOG.error(String.format("error tailing file %s", file), e);

                throw new RuntimeException(e);
              }
            }
          }
        } catch (RuntimeException e) {
          if (e.getCause() instanceof ChecksumException) {
            error.set(true);
          }

          writer.interrupt();
          LOG.error("error in tailer", e);
          throw e;
        }
      }
    });

    writer.start();
    tailer.start();

    try {
      writer.join();
      tailer.join();

      assertFalse(
        "error occurred, see log above", error.get()
      );
    } catch (InterruptedException e) {
      LOG.info("interrupted waiting for writer or tailer to complete");

      Thread.currentThread().interrupt();
    }
  }

};