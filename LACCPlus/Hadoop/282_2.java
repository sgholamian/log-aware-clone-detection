//,temp,S3AInputStream.java,152,180,temp,S3AInputStream.java,123,150
//,3
public class xxx {
  @Override
  public synchronized int read() throws IOException {
    checkNotClosed();

    openIfNeeded();

    int byteRead;
    try {
      byteRead = wrappedStream.read();
    } catch (SocketTimeoutException e) {
      LOG.info("Got timeout while trying to read from stream, trying to recover " + e);
      reopen(pos);
      byteRead = wrappedStream.read();
    } catch (SocketException e) {
      LOG.info("Got socket exception while trying to read from stream, trying to recover " + e);
      reopen(pos);
      byteRead = wrappedStream.read();
    }

    if (byteRead >= 0) {
      pos++;
    }

    if (stats != null && byteRead >= 0) {
      stats.incrementBytesRead(1);
    }
    return byteRead;
  }

};