//,temp,TestSequenceFile.java,450,458,temp,TestHistoryViewerPrinter.java,1034,1042
//,3
public class xxx {
  @SuppressWarnings("deprecation")
  private SequenceFile.Metadata readMetadata(FileSystem fs, Path file)
    throws IOException {
    LOG.info("reading file: " + file.toString());
    SequenceFile.Reader reader = new SequenceFile.Reader(fs, file, conf);
    SequenceFile.Metadata meta = reader.getMetadata(); 
    reader.close();
    return meta;
  }

};