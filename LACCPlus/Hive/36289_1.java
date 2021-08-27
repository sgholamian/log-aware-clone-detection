//,temp,FileList.java,161,168,temp,FileList.java,152,159
//,2
public class xxx {
  FSDataOutputStream getWriterAppendMode() throws IOException {
    try {
      return backingFile.getFileSystem(conf).append(backingFile);
    } catch (IOException e) {
      LOG.error("Error opening file {} in append mode", backingFile, e);
      throw e;
    }
  }

};