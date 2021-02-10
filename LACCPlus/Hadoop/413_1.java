//,temp,TestWebHDFS.java,213,234,temp,TestWebHDFS.java,189,211
//,3
public class xxx {
  static void verifyPread(FileSystem fs, Path p, long offset, long length,
      byte[] buf, byte[] expected) throws IOException {
    long remaining = length - offset;
    long checked = 0;
    LOG.info("XXX PREAD: offset=" + offset + ", remaining=" + remaining);

    final Ticker t = new Ticker("PREAD", "offset=%d, remaining=%d",
        offset, remaining);
    final FSDataInputStream in = fs.open(p, 64 << 10);
    for(; remaining > 0; ) {
      t.tick(checked, "offset=%d, remaining=%d", offset, remaining);
      final int n = (int)Math.min(remaining, buf.length);
      in.readFully(offset, buf, 0, n);
      checkData(offset, remaining, n, buf, expected);

      offset += n;
      remaining -= n;
      checked += n;
    }
    in.close();
    t.end(checked);
  }

};