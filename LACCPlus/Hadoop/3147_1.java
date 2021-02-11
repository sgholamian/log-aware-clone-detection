//,temp,TestCachingStrategy.java,185,212,temp,TestCachingStrategy.java,161,183
//,3
public class xxx {
  static long readHdfsFile(FileSystem fs, Path p, long length,
      Boolean dropBehind) throws Exception {
    FSDataInputStream fis = null;
    long totalRead = 0;
    try {
      fis = fs.open(p);
      if (dropBehind != null) {
        fis.setDropBehind(dropBehind);
      }
      byte buf[] = new byte[8196];
      while (length > 0) {
        int amt = (length > buf.length) ? buf.length : (int)length;
        int ret = fis.read(buf, 0, amt);
        if (ret == -1) {
          return totalRead;
        }
        totalRead += ret;
        length -= ret;
      }
    } catch (IOException e) {
      LOG.error("ioexception", e);
    } finally {
      if (fis != null) {
        fis.close();
      }
    }
    throw new RuntimeException("unreachable");
  }

};