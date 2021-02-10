//,temp,JobHistoryEventHandler.java,1630,1638,temp,WebHdfsFileSystem.java,1511,1522
//,3
public class xxx {
    void closeWriter() throws IOException {
      LOG.debug("Closing Writer");
      synchronized (lock) {
        if (writer != null) {
          writer.close();
        }
        writer = null;
      }
    }

};