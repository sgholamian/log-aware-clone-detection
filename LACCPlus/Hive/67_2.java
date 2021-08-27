//,temp,ZooKeeperHiveHelper.java,352,363,temp,FileList.java,226,240
//,3
public class xxx {
  @Override
  public void close() throws IOException {
    try {
      if (backingFileReader != null) {
        backingFileReader.close();
      }
      if (backingFileWriter != null) {
        backingFileWriter.close();
      }
      LOG.info("Completed close for File List backed by:{}", backingFile);
    } finally {
      backingFileReader = null;
      backingFileWriter = null;
    }
  }

};