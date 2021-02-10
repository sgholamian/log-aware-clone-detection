//,temp,TestDFSIO.java,1103,1107,temp,JHLogAnalyzer.java,1125,1129
//,3
public class xxx {
  private void cleanup(FileSystem fs)
  throws IOException {
    LOG.info("Cleaning up test files");
    fs.delete(new Path(getBaseDir(config)), true);
  }

};