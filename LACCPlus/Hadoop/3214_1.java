//,temp,FSImageLoader.java,109,176,temp,PBImageTextWriter.java,431,489
//,3
public class xxx {
  static FSImageLoader load(String inputFile) throws IOException {
    Configuration conf = new Configuration();
    RandomAccessFile file = new RandomAccessFile(inputFile, "r");
    if (!FSImageUtil.checkFileFormat(file)) {
      throw new IOException("Unrecognized FSImage");
    }

    FsImageProto.FileSummary summary = FSImageUtil.loadSummary(file);


    try (FileInputStream fin = new FileInputStream(file.getFD())) {
      // Map to record INodeReference to the referred id
      ImmutableList<Long> refIdList = null;
      String[] stringTable = null;
      byte[][] inodes = null;
      Map<Long, long[]> dirmap = null;

      ArrayList<FsImageProto.FileSummary.Section> sections =
          Lists.newArrayList(summary.getSectionsList());
      Collections.sort(sections,
          new Comparator<FsImageProto.FileSummary.Section>() {
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

      for (FsImageProto.FileSummary.Section s : sections) {
        fin.getChannel().position(s.getOffset());
        InputStream is = FSImageUtil.wrapInputStreamForCompression(conf,
            summary.getCodec(), new BufferedInputStream(new LimitInputStream(
            fin, s.getLength())));

        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading section " + s.getName() + " length: " + s.getLength
              ());
        }
        switch (FSImageFormatProtobuf.SectionName.fromString(s.getName())) {
          case STRING_TABLE:
            stringTable = loadStringTable(is);
            break;
          case INODE:
            inodes = loadINodeSection(is);
            break;
          case INODE_REFERENCE:
            refIdList = loadINodeReferenceSection(is);
            break;
          case INODE_DIR:
            dirmap = loadINodeDirectorySection(is, refIdList);
            break;
          default:
            break;
        }
      }
      return new FSImageLoader(stringTable, inodes, dirmap);
    }
  }

};