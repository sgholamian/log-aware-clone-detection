//,temp,TestDFSIO.java,292,330,temp,DFSCIOTest.java,122,149
//,3
public class xxx {
  @SuppressWarnings("deprecation")
  private void createControlFile(FileSystem fs,
                                  long nrBytes, // in bytes
                                  int nrFiles
                                ) throws IOException {
    LOG.info("creating control file: "+nrBytes+" bytes, "+nrFiles+" files");
    final int maxDirItems = config.getInt(
        DFSConfigKeys.DFS_NAMENODE_MAX_DIRECTORY_ITEMS_KEY,
        DFSConfigKeys.DFS_NAMENODE_MAX_DIRECTORY_ITEMS_DEFAULT);
    Path controlDir = getControlDir(config);

    if (nrFiles > maxDirItems) {
      final String message = "The directory item limit of " + controlDir +
          " is exceeded: limit=" + maxDirItems + " items=" + nrFiles;
      throw new IOException(message);
    }

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
        if (writer != null) {
          writer.close();
        }
        writer = null;
      }
    }
    LOG.info("created control files for: " + nrFiles + " files");
  }

};