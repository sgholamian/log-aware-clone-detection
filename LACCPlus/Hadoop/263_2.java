//,temp,TestSetFile.java,103,115,temp,TestArrayFile.java,74,86
//,3
public class xxx {
  private static void writeTest(FileSystem fs, RandomDatum[] data, String file)
    throws IOException {
    Configuration conf = new Configuration();
    MapFile.delete(fs, file);
    if(LOG.isDebugEnabled()) {
      LOG.debug("creating with " + data.length + " debug");
    }
    ArrayFile.Writer writer = new ArrayFile.Writer(conf, fs, file, RandomDatum.class);
    writer.setIndexInterval(100);
    for (int i = 0; i < data.length; i++)
      writer.append(data[i]);
    writer.close();
  }

};