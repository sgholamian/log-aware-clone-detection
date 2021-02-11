//,temp,IOUtils.java,319,327,temp,WebHdfsFileSystem.java,1511,1522
//,3
public class xxx {
  public static void closeSocket(Socket sock) {
    if (sock != null) {
      try {
        sock.close();
      } catch (IOException ignored) {
        LOG.debug("Ignoring exception while closing socket", ignored);
      }
    }
  }

};