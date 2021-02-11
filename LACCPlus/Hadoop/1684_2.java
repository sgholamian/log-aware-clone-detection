//,temp,NameNodeRpcServer.java,1338,1352,temp,NameNodeRpcServer.java,1325,1336
//,3
public class xxx {
  @Override
  public DatanodeCommand cacheReport(DatanodeRegistration nodeReg,
      String poolId, List<Long> blockIds) throws IOException {
    checkNNStartup();
    verifyRequest(nodeReg);
    if (blockStateChangeLog.isDebugEnabled()) {
      blockStateChangeLog.debug("*BLOCK* NameNode.cacheReport: "
           + "from " + nodeReg + " " + blockIds.size() + " blocks");
    }
    namesystem.getCacheManager().processCacheReport(nodeReg, blockIds);
    return null;
  }

};