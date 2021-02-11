//,temp,BlockReaderLocal.java,549,586,temp,BlockReaderLocal.java,398,433
//,3
public class xxx {
  @Override
  public synchronized int read(byte[] arr, int off, int len)
        throws IOException {
    boolean canSkipChecksum = createNoChecksumContext();
    int nRead;
    try {
      String traceString = null;
      if (LOG.isTraceEnabled()) {
        traceString = new StringBuilder().
            append("read(arr.length=").append(arr.length).
            append(", off=").append(off).
            append(", len=").append(len).
            append(", filename=").append(filename).
            append(", block=").append(block).
            append(", canSkipChecksum=").append(canSkipChecksum).
            append(")").toString();
        LOG.trace(traceString + ": starting");
      }
      try {
        if (canSkipChecksum && zeroReadaheadRequested) {
          nRead = readWithoutBounceBuffer(arr, off, len);
        } else {
          nRead = readWithBounceBuffer(arr, off, len, canSkipChecksum);
        }
      } catch (IOException e) {
        if (LOG.isTraceEnabled()) {
          LOG.trace(traceString + ": I/O error", e);
        }
        throw e;
      }
      if (LOG.isTraceEnabled()) {
        LOG.trace(traceString + ": returning " + nRead);
      }
    } finally {
      if (canSkipChecksum) releaseNoChecksumContext();
    }
    return nRead;
  }

};