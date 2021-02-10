//,temp,TestFileAppend3.java,251,286,temp,TestFileAppend3.java,215,249
//,3
public class xxx {
  @Test
  public void testTC5ForAppend2() throws Exception {
    final Path p = new Path("/TC5/foo2");

    // a. Create file on Machine M1. Write half block to it. Close file.
    {
      FSDataOutputStream out = fs.create(p, false, buffersize, REPLICATION,
          BLOCK_SIZE);
      AppendTestUtil.write(out, 0, (int)(BLOCK_SIZE/2));
      out.close();
    }

    // b. Reopen file in "append" mode on Machine M1.
    FSDataOutputStream out = fs.append(p, EnumSet.of(CreateFlag.APPEND, CreateFlag.NEW_BLOCK),
        4096, null);

    // c. On Machine M2, reopen file in "append" mode. This should fail.
    try {
      ((DistributedFileSystem) AppendTestUtil
          .createHdfsWithDifferentUsername(conf)).append(p,
          EnumSet.of(CreateFlag.APPEND, CreateFlag.NEW_BLOCK), 4096, null);
      fail("This should fail.");
    } catch(IOException ioe) {
      AppendTestUtil.LOG.info("GOOD: got an exception", ioe);
    }

    try {
      AppendTestUtil.createHdfsWithDifferentUsername(conf).append(p);
      fail("This should fail.");
    } catch(IOException ioe) {
      AppendTestUtil.LOG.info("GOOD: got an exception", ioe);
    }

    // d. On Machine M1, close file.
    out.close();
  }

};