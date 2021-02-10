//,temp,HttpInputStreamWithRelease.java,187,209,temp,HttpInputStreamWithRelease.java,166,185
//,3
public class xxx {
  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    SwiftUtils.validateReadArgs(b, off, len);
    //if the stream is already closed, then report an exception.
    assumeNotReleased();
    //now read in a buffer, reacting differently to different operations
    int read;
    try {
      read = inStream.read(b, off, len);
    } catch (EOFException e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("EOF exception " + e, e);
      }
      read = -1;
    } catch (IOException e) {
      throw releaseAndRethrow("read(b, off, " + len + ")", e);
    }
    if (read < 0) {
      dataConsumed = true;
      release("read() -all data consumed", null);
    }
    return read;
  }

};