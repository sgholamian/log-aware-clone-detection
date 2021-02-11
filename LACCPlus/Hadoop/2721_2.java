//,temp,AbstractContractSeekTest.java,495,512,temp,AbstractContractSeekTest.java,463,493
//,3
public class xxx {
  @Test
  public void testReadFullyPastEOF() throws Throwable {
    describe("readFully past the EOF of a file");
    assumeSupportsPositionedReadable();
    instream = getFileSystem().open(smallSeekFile);
    byte[] buffer = new byte[256];

    // now read past the end of the file
    try {
      instream.readFully(TEST_FILE_LEN + 1, buffer);
      fail("Expected an exception");
    } catch (EOFException e) {
      handleExpectedException(e);
    } catch (IOException e) {
      handleRelaxedException("readFully with an offset past EOF ",
          "EOFException",
          e);
    }
    // read zero bytes from an offset past EOF.
    try {
      instream.readFully(TEST_FILE_LEN + 1, buffer, 0, 0);
      // a zero byte read may fail-fast
      LOG.info("Filesystem short-circuits 0-byte reads");
    } catch (EOFException e) {
      handleExpectedException(e);
    } catch (IOException e) {
      handleRelaxedException("readFully(0 bytes) with an offset past EOF ",
          "EOFException",
          e);
    }
  }

};