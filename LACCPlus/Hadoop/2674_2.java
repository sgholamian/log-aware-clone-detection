//,temp,TestDFSIO.java,1103,1107,temp,JHLogAnalyzer.java,1125,1129
//,3
public class xxx {
  private static void cleanup(Configuration conf) throws IOException {
    LOG.info("Cleaning up test files");
    FileSystem fs = FileSystem.get(conf);
    fs.delete(new Path(JHLA_ROOT_DIR), true);
  }

};