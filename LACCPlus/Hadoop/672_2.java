//,temp,AbstractContractSeekTest.java,164,183,temp,TestSeek.java,133,148
//,3
public class xxx {
  @Test(timeout = SWIFT_TEST_TIMEOUT)
  public void testNegativeSeek() throws Throwable {
    instream = fs.open(smallSeekFile);
    assertEquals(0, instream.getPos());
    try {
      instream.seek(-1);
      long p = instream.getPos();
      LOG.warn("Seek to -1 returned a position of " + p);
      int result = instream.read();
      fail(
        "expected an exception, got data " + result + " at a position of " + p);
    } catch (IOException e) {
      //bad seek -expected
    }
    assertEquals(0, instream.getPos());
  }

};