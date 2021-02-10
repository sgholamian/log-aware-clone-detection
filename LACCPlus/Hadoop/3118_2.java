//,temp,PBImageTextWriter.java,535,553,temp,PBImageTextWriter.java,515,533
//,3
public class xxx {
  private void loadDirectories(
      FileInputStream fin, List<FileSummary.Section> sections,
      FileSummary summary, Configuration conf)
      throws IOException {
    LOG.info("Loading directories");
    long startTime = Time.monotonicNow();
    for (FileSummary.Section section : sections) {
      if (SectionName.fromString(section.getName())
          == SectionName.INODE) {
        fin.getChannel().position(section.getOffset());
        InputStream is = FSImageUtil.wrapInputStreamForCompression(conf,
            summary.getCodec(), new BufferedInputStream(new LimitInputStream(
                fin, section.getLength())));
        loadDirectoriesInINodeSection(is);
      }
    }
    long timeTaken = Time.monotonicNow() - startTime;
    LOG.info("Finished loading directories in {}ms", timeTaken);
  }

};