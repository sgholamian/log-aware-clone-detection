//,temp,BlockReaderLocal.java,549,586,temp,BlockReaderLocal.java,398,433
//,3
public class xxx {
  @Override
  public synchronized int read(ByteBuffer buf) throws IOException {
    boolean canSkipChecksum = createNoChecksumContext();
    try {
      String traceString = null;
      if (LOG.isTraceEnabled()) {
        traceString = new StringBuilder().
            append("read(").
            append("buf.remaining=").append(buf.remaining()).
            append(", block=").append(block).
            append(", filename=").append(filename).
            append(", canSkipChecksum=").append(canSkipChecksum).
            append(")").toString();
        LOG.info(traceString + ": starting");
      }
      int nRead;
      try {
        if (canSkipChecksum && zeroReadaheadRequested) {
          nRead = readWithoutBounceBuffer(buf);
        } else {
          nRead = readWithBounceBuffer(buf, canSkipChecksum);
        }
      } catch (IOException e) {
        if (LOG.isTraceEnabled()) {
          LOG.info(traceString + ": I/O error", e);
        }
        throw e;
      }
      if (LOG.isTraceEnabled()) {
        LOG.info(traceString + ": returning " + nRead);
      }
      return nRead;
    } finally {
      if (canSkipChecksum) releaseNoChecksumContext();
    }
  }

};