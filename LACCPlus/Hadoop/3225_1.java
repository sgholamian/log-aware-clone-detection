//,temp,TestDecommissionWithStriped.java,533,587,temp,TestDecommission.java,99,146
//,3
public class xxx {
  private static String checkFile(FileSystem fileSys, Path name, int repl,
      List<DatanodeInfo> decommissionedNodes, int numDatanodes)
          throws IOException {
    boolean isNodeDown = decommissionedNodes.size() > 0;
    // need a raw stream
    assertTrue("Not HDFS:" + fileSys.getUri(),
        fileSys instanceof DistributedFileSystem);
    HdfsDataInputStream dis = (HdfsDataInputStream) fileSys.open(name);
    Collection<LocatedBlock> dinfo = dis.getAllBlocks();
    for (LocatedBlock blk : dinfo) { // for each block
      int hasdown = 0;
      DatanodeInfo[] nodes = blk.getLocations();
      for (int j = 0; j < nodes.length; j++) { // for each replica
        LOG.info("Block Locations size={}, locs={}, j=", nodes.length,
            nodes[j].toString(), j);
        boolean found = false;
        for (DatanodeInfo datanodeInfo : decommissionedNodes) {
          // check against decommissioned list
          if (isNodeDown
              && nodes[j].getXferAddr().equals(datanodeInfo.getXferAddr())) {
            found = true;
            hasdown++;
            // Downnode must actually be decommissioned
            if (!nodes[j].isDecommissioned()) {
              return "For block " + blk.getBlock() + " replica on " + nodes[j]
                  + " is given as downnode, " + "but is not decommissioned";
            }
            // Decommissioned node (if any) should only be last node in list.
            if (j < repl) {
              return "For block " + blk.getBlock() + " decommissioned node "
                  + nodes[j] + " was not last node in list: " + (j + 1) + " of "
                  + nodes.length;
            }
            // should only be last node in list.
            LOG.info("Block " + blk.getBlock() + " replica on " + nodes[j]
                + " is decommissioned.");
          }
        }
        // Non-downnodes must not be decommissioned
        if (!found && nodes[j].isDecommissioned()) {
          return "For block " + blk.getBlock() + " replica on " + nodes[j]
              + " is unexpectedly decommissioned";
        }
      }

      LOG.info("Block " + blk.getBlock() + " has " + hasdown
          + " decommissioned replica.");
      if (Math.min(numDatanodes, repl + hasdown) != nodes.length) {
        return "Wrong number of replicas for block " + blk.getBlock() + ": "
            + nodes.length + ", expected "
            + Math.min(numDatanodes, repl + hasdown);
      }
    }
    return null;
  }

};