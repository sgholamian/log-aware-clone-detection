//,temp,TestSequenceFile.java,131,149,temp,DFSCIOTest.java,122,149
//,3
public class xxx {
  private static void createControlFile(
                                        FileSystem fs,
                                        int fileSize, // in MB 
                                        int nrFiles
                                        ) throws IOException {
    LOG.info("creating control file: "+fileSize+" mega bytes, "+nrFiles+" files");

    fs.delete(CONTROL_DIR, true);

    for(int i=0; i < nrFiles; i++) {
      String name = getFileName(i);
      Path controlFile = new Path(CONTROL_DIR, "in_file_" + name);
      SequenceFile.Writer writer = null;
      try {
        writer = SequenceFile.createWriter(fs, fsConfig, controlFile,
                                           Text.class, LongWritable.class,
                                           CompressionType.NONE);
        writer.append(new Text(name), new LongWritable(fileSize));
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