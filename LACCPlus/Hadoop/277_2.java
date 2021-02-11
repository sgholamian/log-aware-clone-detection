//,temp,HttpInputStreamWithRelease.java,187,209,temp,HttpInputStreamWithRelease.java,166,185
//,3
public class xxx {
  @Override
  public int read() throws IOException {
    assumeNotReleased();
    int read = 0;
    try {
      read = inStream.read();
    } catch (EOFException e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("EOF exception " + e, e);
      }
      read = -1;
    } catch (IOException e) {
      throw releaseAndRethrow("read()", e);
    }
    if (read < 0) {
      dataConsumed = true;
      release("read() -all data consumed", null);
    }
    return read;
  }

};