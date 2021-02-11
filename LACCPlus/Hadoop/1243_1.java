//,temp,PBImageTextWriter.java,575,588,temp,PBImageTextWriter.java,532,547
//,3
public class xxx {
  private void outputINodes(InputStream in) throws IOException {
    INodeSection s = INodeSection.parseDelimitedFrom(in);
    LOG.info("Found {} INodes in the INode section", s.getNumInodes());
    for (int i = 0; i < s.getNumInodes(); ++i) {
      INode p = INode.parseDelimitedFrom(in);
      String parentPath = metadataMap.getParentPath(p.getId());
      out.println(getEntry(parentPath, p));

      if (LOG.isDebugEnabled() && i % 100000 == 0) {
        LOG.debug("Outputted {} INodes.", i);
      }
    }
    LOG.info("Outputted {} INodes.", s.getNumInodes());
  }

};