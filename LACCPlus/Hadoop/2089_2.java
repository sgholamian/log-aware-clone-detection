//,temp,TestFileConcurrentReader.java,324,416,temp,TestFileConcurrentReader.java,203,276
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testImmediateReadOfNewFile()
    throws IOException {
    final int blockSize = 64 * 1024;
    final int writeSize = 10 * blockSize;
    Configuration conf = new Configuration();
    
    conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, blockSize);
    init(conf);

    final int requiredSuccessfulOpens = 100;
    final Path file = new Path("/file1");
    final AtomicBoolean openerDone = new AtomicBoolean(false);
    final AtomicReference<String> errorMessage = new AtomicReference<String>();
    final FSDataOutputStream out = fileSystem.create(file);
    
    final Thread writer = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (!openerDone.get()) {
            out.write(DFSTestUtil.generateSequentialBytes(0, writeSize));
            out.hflush();
          }
        } catch (IOException e) {
          LOG.warn("error in writer", e);
        } finally {
          try {
            out.close();
          } catch (IOException e) {
            LOG.error("unable to close file");
          }
        }
      }
    });
    
    Thread opener = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          for (int i = 0; i < requiredSuccessfulOpens; i++) {
            fileSystem.open(file).close();
          }
          openerDone.set(true);
        } catch (IOException e) {
          openerDone.set(true);
          errorMessage.set(String.format(
            "got exception : %s",
            StringUtils.stringifyException(e)
          ));
        } catch (Exception e) {
          openerDone.set(true);
          errorMessage.set(String.format(
            "got exception : %s", 
            StringUtils.stringifyException(e)
          ));
          writer.interrupt();
          fail("here");
        }
      }
    });
    
    writer.start();
    opener.start();

    try {
      writer.join();
      opener.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    
    assertNull(errorMessage.get(), errorMessage.get());
  }

};