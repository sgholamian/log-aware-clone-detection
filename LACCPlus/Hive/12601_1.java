//,temp,AvroGenericRecordReader.java,192,212,temp,AvroGenericRecordReader.java,170,190
//,2
public class xxx {
  private Boolean extractWriterProlepticFromMetadata(JobConf job, FileSplit split,
      GenericDatumReader<GenericRecord> gdr) {
    if (job == null || gdr == null || split == null || split.getPath() == null) {
      return null;
    }
    try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(
        new FsInput(split.getPath(), job), gdr)) {
      if (dataFileReader.getMeta(AvroSerDe.WRITER_PROLEPTIC) != null) {
        try {
          return Boolean.valueOf(new String(dataFileReader.getMeta(AvroSerDe.WRITER_PROLEPTIC),
              StandardCharsets.UTF_8));
        } catch (DateTimeException e) {
          throw new RuntimeException("Can't parse writer proleptic property stored in file metadata", e);
        }
      }
    } catch (IOException e) {
      // Can't access metadata, carry on.
      LOG.debug(e.getMessage(), e);
    }
    return null;
  }

};