//,temp,S3AInputStream.java,152,180,temp,S3AInputStream.java,123,150
//,3
public class xxx {
  @Override
  public synchronized int read(byte[] buf, int off, int len) throws IOException {
    checkNotClosed();

    openIfNeeded();

    int byteRead;
    try {
      byteRead = wrappedStream.read(buf, off, len);
    } catch (SocketTimeoutException e) {
      LOG.info("Got timeout while trying to read from stream, trying to recover " + e);
      reopen(pos);
      byteRead = wrappedStream.read(buf, off, len);
    } catch (SocketException e) {
      LOG.info("Got socket exception while trying to read from stream, trying to recover " + e);
      reopen(pos);
      byteRead = wrappedStream.read(buf, off, len);
    }

    if (byteRead > 0) {
      pos += byteRead;
    }

    if (stats != null && byteRead > 0) {
      stats.incrementBytesRead(byteRead);
    }

    return byteRead;
  }

};