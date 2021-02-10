//,temp,FSImageLoader.java,178,205,temp,PBImageTextWriter.java,578,603
//,3
public class xxx {
  private void buildNamespace(InputStream in, List<Long> refIdList)
      throws IOException {
    int count = 0;
    while (true) {
      FsImageProto.INodeDirectorySection.DirEntry e =
          FsImageProto.INodeDirectorySection.DirEntry.parseDelimitedFrom(in);
      if (e == null) {
        break;
      }
      count++;
      if (LOG.isDebugEnabled() && count % 10000 == 0) {
        LOG.debug("Scanned {} directories.", count);
      }
      long parentId = e.getParent();
      for (int i = 0; i < e.getChildrenCount(); i++) {
        long childId = e.getChildren(i);
        metadataMap.putDirChild(parentId, childId);
      }
      for (int i = e.getChildrenCount();
           i < e.getChildrenCount() + e.getRefChildrenCount(); i++) {
        int refId = e.getRefChildren(i - e.getChildrenCount());
        metadataMap.putDirChild(parentId, refIdList.get(refId));
      }
    }
    LOG.info("Scanned {} INode directories to build namespace.", count);
  }

};