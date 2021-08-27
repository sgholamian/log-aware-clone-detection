//,temp,FileList.java,161,168,temp,FileList.java,152,159
//,2
public class xxx {
  FSDataOutputStream getWriterCreateMode() throws IOException {
    try {
      return backingFile.getFileSystem(conf).create(backingFile);
    } catch (IOException e) {
      LOG.error("Error creating file {}", backingFile, e);
      throw e;
    }
  }

};