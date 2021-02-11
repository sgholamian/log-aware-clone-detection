//,temp,PBImageTextWriter.java,535,553,temp,PBImageTextWriter.java,515,533
//,3
public class xxx {
  private void loadINodeDirSection(
      FileInputStream fin, List<FileSummary.Section> sections,
      FileSummary summary, Configuration conf, List<Long> refIdList)
      throws IOException {
    LOG.info("Loading INode directory section.");
    long startTime = Time.monotonicNow();
    for (FileSummary.Section section : sections) {
      if (SectionName.fromString(section.getName())
          == SectionName.INODE_DIR) {
        fin.getChannel().position(section.getOffset());
        InputStream is = FSImageUtil.wrapInputStreamForCompression(conf,
            summary.getCodec(), new BufferedInputStream(
                new LimitInputStream(fin, section.getLength())));
        buildNamespace(is, refIdList);
      }
    }
    long timeTaken = Time.monotonicNow() - startTime;
    LOG.info("Finished loading INode directory section in {}ms", timeTaken);
  }

};