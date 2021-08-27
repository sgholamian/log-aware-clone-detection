//,temp,AvroGenericRecordReader.java,192,212,temp,AvroGenericRecordReader.java,170,190
//,2
public class xxx {
  private ZoneId extractWriterTimezoneFromMetadata(JobConf job, FileSplit split,
      GenericDatumReader<GenericRecord> gdr) {
    if (job == null || gdr == null || split == null || split.getPath() == null) {
      return null;
    }
    try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(
        new FsInput(split.getPath(), job), gdr)) {
      if (dataFileReader.getMeta(AvroSerDe.WRITER_TIME_ZONE) != null) {
        try {
          return ZoneId.of(new String(dataFileReader.getMeta(AvroSerDe.WRITER_TIME_ZONE),
              StandardCharsets.UTF_8));
        } catch (DateTimeException e) {
          throw new RuntimeException("Can't parse writer time zone stored in file metadata", e);
        }
      }
    } catch (IOException e) {
      // Can't access metadata, carry on.
      LOG.debug(e.getMessage(), e);
    }
    return null;
  }

};