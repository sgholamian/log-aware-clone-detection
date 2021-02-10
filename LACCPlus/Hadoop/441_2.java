//,temp,TestSequenceFile.java,384,402,temp,TestSequenceFile.java,131,149
//,3
public class xxx {
  private void writeTest(FileSystem fs, int count, int seed, Path file, 
                                CompressionType compressionType, CompressionCodec codec)
    throws IOException {
    fs.delete(file, true);
    LOG.info("creating " + count + " records with " + compressionType +
             " compression");
    SequenceFile.Writer writer = 
      SequenceFile.createWriter(fs, conf, file, 
                                RandomDatum.class, RandomDatum.class, compressionType, codec);
    RandomDatum.Generator generator = new RandomDatum.Generator(seed);
    for (int i = 0; i < count; i++) {
      generator.next();
      RandomDatum key = generator.getKey();
      RandomDatum value = generator.getValue();

      writer.append(key, value);
    }
    writer.close();
  }

};