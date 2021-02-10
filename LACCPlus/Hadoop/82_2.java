//,temp,PBImageTextWriter.java,489,507,temp,PBImageTextWriter.java,466,482
//,3
public class xxx {
  private void output(Configuration conf, FileSummary summary,
      FileInputStream fin, ArrayList<FileSummary.Section> sections)
      throws IOException {
    InputStream is;
    long startTime = Time.monotonicNow();
    for (FileSummary.Section section : sections) {
      if (SectionName.fromString(section.getName()) == SectionName.INODE) {
        fin.getChannel().position(section.getOffset());
        is = FSImageUtil.wrapInputStreamForCompression(conf,
            summary.getCodec(), new BufferedInputStream(new LimitInputStream(
                fin, section.getLength())));
        outputINodes(is);
      }
    }
    long timeTaken = Time.monotonicNow() - startTime;
    LOG.debug("Time to output inodes: {}ms", timeTaken);
  }

};