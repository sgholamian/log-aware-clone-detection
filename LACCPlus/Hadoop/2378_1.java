//,temp,FSImageLoader.java,178,205,temp,PBImageTextWriter.java,578,603
//,3
public class xxx {
  private static Map<Long, long[]> loadINodeDirectorySection
          (InputStream in, List<Long> refIdList)
      throws IOException {
    LOG.info("Loading inode directory section");
    Map<Long, long[]> dirs = Maps.newHashMap();
    long counter = 0;
    while (true) {
      FsImageProto.INodeDirectorySection.DirEntry e =
          FsImageProto.INodeDirectorySection.DirEntry.parseDelimitedFrom(in);
      // note that in is a LimitedInputStream
      if (e == null) {
        break;
      }
      ++counter;

      long[] l = new long[e.getChildrenCount() + e.getRefChildrenCount()];
      for (int i = 0; i < e.getChildrenCount(); ++i) {
        l[i] = e.getChildren(i);
      }
      for (int i = e.getChildrenCount(); i < l.length; i++) {
        int refId = e.getRefChildren(i - e.getChildrenCount());
        l[i] = refIdList.get(refId);
      }
      dirs.put(e.getParent(), l);
    }
    LOG.info("Loaded " + counter + " directories");
    return dirs;
  }

};