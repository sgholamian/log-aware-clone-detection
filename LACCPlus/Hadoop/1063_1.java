//,temp,TestDFSIO.java,296,324,temp,DFSCIOTest.java,122,149
//,3
public class xxx {
  @SuppressWarnings("deprecation")
  private void createControlFile(FileSystem fs,
                                  long nrBytes, // in bytes
                                  int nrFiles
                                ) throws IOException {
    LOG.info("creating control file: "+nrBytes+" bytes, "+nrFiles+" files");

    Path controlDir = getControlDir(config);
    fs.delete(controlDir, true);

    for(int i=0; i < nrFiles; i++) {
      String name = getFileName(i);
      Path controlFile = new Path(controlDir, "in_file_" + name);
      SequenceFile.Writer writer = null;
      try {
        writer = SequenceFile.createWriter(fs, config, controlFile,
                                           Text.class, LongWritable.class,
                                           CompressionType.NONE);
        writer.append(new Text(name), new LongWritable(nrBytes));
      } catch(Exception e) {
        throw new IOException(e.getLocalizedMessage());
      } finally {
        if (writer != null)
          writer.close();
        writer = null;
      }
    }
    LOG.info("created control files for: "+nrFiles+" files");
  }

};