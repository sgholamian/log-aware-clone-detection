//,temp,FSImageLoader.java,109,176,temp,PBImageTextWriter.java,431,489
//,3
public class xxx {
  public void visit(RandomAccessFile file) throws IOException {
    Configuration conf = new Configuration();
    if (!FSImageUtil.checkFileFormat(file)) {
      throw new IOException("Unrecognized FSImage");
    }

    FileSummary summary = FSImageUtil.loadSummary(file);

    try (FileInputStream fin = new FileInputStream(file.getFD())) {
      InputStream is;
      ArrayList<FileSummary.Section> sections =
          Lists.newArrayList(summary.getSectionsList());
      Collections.sort(sections,
          new Comparator<FileSummary.Section>() {
            @Override
            public int compare(FsImageProto.FileSummary.Section s1,
                FsImageProto.FileSummary.Section s2) {
              FSImageFormatProtobuf.SectionName n1 =
                  FSImageFormatProtobuf.SectionName.fromString(s1.getName());
              FSImageFormatProtobuf.SectionName n2 =
                  FSImageFormatProtobuf.SectionName.fromString(s2.getName());
              if (n1 == null) {
                return n2 == null ? 0 : -1;
              } else if (n2 == null) {
                return -1;
              } else {
                return n1.ordinal() - n2.ordinal();
              }
            }
          });

      ImmutableList<Long> refIdList = null;
      for (FileSummary.Section section : sections) {
        fin.getChannel().position(section.getOffset());
        is = FSImageUtil.wrapInputStreamForCompression(conf,
            summary.getCodec(), new BufferedInputStream(new LimitInputStream(
                fin, section.getLength())));
        switch (SectionName.fromString(section.getName())) {
        case STRING_TABLE:
          LOG.info("Loading string table");
          stringTable = FSImageLoader.loadStringTable(is);
          break;
        case INODE_REFERENCE:
          // Load INodeReference so that all INodes can be processed.
          // Snapshots are not handled and will just be ignored for now.
          LOG.info("Loading inode references");
          refIdList = FSImageLoader.loadINodeReferenceSection(is);
          break;
        default:
          break;
        }
      }

      loadDirectories(fin, sections, summary, conf);
      loadINodeDirSection(fin, sections, summary, conf, refIdList);
      metadataMap.sync();
      output(conf, summary, fin, sections);
    }
  }

};