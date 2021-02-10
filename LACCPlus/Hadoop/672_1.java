//,temp,AbstractContractSeekTest.java,164,183,temp,TestSeek.java,133,148
//,3
public class xxx {
  @Test
  public void testNegativeSeek() throws Throwable {
    instream = getFileSystem().open(smallSeekFile);
    assertEquals(0, instream.getPos());
    try {
      instream.seek(-1);
      long p = instream.getPos();
      LOG.warn("Seek to -1 returned a position of " + p);
      int result = instream.read();
      fail(
        "expected an exception, got data " + result + " at a position of " + p);
    } catch (EOFException e) {
      //bad seek -expected
      handleExpectedException(e);
    } catch (IOException e) {
      //bad seek -expected, but not as preferred as an EOFException
      handleRelaxedException("a negative seek", "EOFException", e);
    }
    assertEquals(0, instream.getPos());
  }

};