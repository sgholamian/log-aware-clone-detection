//,temp,MasterRpcServices.java,1550,1589,temp,MasterRpcServices.java,582,611
//,3
public class xxx {
  @Override
  public AssignRegionResponse assignRegion(RpcController controller,
      AssignRegionRequest req) throws ServiceException {
    try {
      master.checkInitialized();

      final RegionSpecifierType type = req.getRegion().getType();
      if (type != RegionSpecifierType.REGION_NAME) {
        LOG.warn("assignRegion specifier type: expected: " + RegionSpecifierType.REGION_NAME
          + " actual: " + type);
      }

      final byte[] regionName = req.getRegion().getValue().toByteArray();
      final RegionInfo regionInfo = master.getAssignmentManager().getRegionInfo(regionName);
      if (regionInfo == null) throw new UnknownRegionException(Bytes.toStringBinary(regionName));

      final AssignRegionResponse arr = AssignRegionResponse.newBuilder().build();
      if (master.cpHost != null) {
        master.cpHost.preAssign(regionInfo);
      }
      LOG.info(master.getClientIdAuditPrefix() + " assign " + regionInfo.getRegionNameAsString());
      master.getAssignmentManager().assign(regionInfo);
      if (master.cpHost != null) {
        master.cpHost.postAssign(regionInfo);
      }
      return arr;
    } catch (IOException ioe) {
      throw new ServiceException(ioe);
    }
  }

};