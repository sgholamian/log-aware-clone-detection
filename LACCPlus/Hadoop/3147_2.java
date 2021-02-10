//,temp,TestCachingStrategy.java,185,212,temp,TestCachingStrategy.java,161,183
//,3
public class xxx {
  static void createHdfsFile(FileSystem fs, Path p, long length,
        Boolean dropBehind) throws Exception {
    FSDataOutputStream fos = null;
    try {
      // create file with replication factor of 1
      fos = fs.create(p, (short)1);
      if (dropBehind != null) {
        fos.setDropBehind(dropBehind);
      }
      byte buf[] = new byte[8196];
      while (length > 0) {
        int amt = (length > buf.length) ? buf.length : (int)length;
        fos.write(buf, 0, amt);
        length -= amt;
      }
    } catch (IOException e) {
      LOG.error("ioexception", e);
    } finally {
      if (fos != null) {
        fos.close();
      }
    }
  }

};