//,temp,TestSequenceFile.java,384,402,temp,TestSequenceFile.java,131,149
//,3
public class xxx {
  private void writeMetadataTest(FileSystem fs, int count, int seed, Path file, 
                                        CompressionType compressionType, CompressionCodec codec, SequenceFile.Metadata metadata)
    throws IOException {
    fs.delete(file, true);
    LOG.info("creating " + count + " records with metadata and with " + compressionType +
             " compression");
    SequenceFile.Writer writer = 
      SequenceFile.createWriter(fs, conf, file, 
                                RandomDatum.class, RandomDatum.class, compressionType, codec, null, metadata);
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