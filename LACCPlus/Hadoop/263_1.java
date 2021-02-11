//,temp,TestSetFile.java,103,115,temp,TestArrayFile.java,74,86
//,3
public class xxx {
  private static void writeTest(FileSystem fs, RandomDatum[] data,
                                String file, CompressionType compress)
    throws IOException {
    MapFile.delete(fs, file);
    LOG.info("creating with " + data.length + " records");
    SetFile.Writer writer =
      new SetFile.Writer(conf, fs, file,
                         WritableComparator.get(RandomDatum.class),
                         compress);
    for (int i = 0; i < data.length; i++)
      writer.append(data[i]);
    writer.close();
  }

};