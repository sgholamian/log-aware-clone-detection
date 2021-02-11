//,temp,MasterRpcServices.java,1550,1589,temp,MasterRpcServices.java,582,611
//,3
public class xxx {
  @Override
  public UnassignRegionResponse unassignRegion(RpcController controller,
      UnassignRegionRequest req) throws ServiceException {
    try {
      final byte [] regionName = req.getRegion().getValue().toByteArray();
      RegionSpecifierType type = req.getRegion().getType();
      final boolean force = req.getForce();
      UnassignRegionResponse urr = UnassignRegionResponse.newBuilder().build();

      master.checkInitialized();
      if (type != RegionSpecifierType.REGION_NAME) {
        LOG.warn("unassignRegion specifier type: expected: " + RegionSpecifierType.REGION_NAME
          + " actual: " + type);
      }
      Pair<RegionInfo, ServerName> pair =
        MetaTableAccessor.getRegion(master.getConnection(), regionName);
      if (Bytes.equals(RegionInfoBuilder.FIRST_META_REGIONINFO.getRegionName(), regionName)) {
        pair = new Pair<>(RegionInfoBuilder.FIRST_META_REGIONINFO,
          MetaTableLocator.getMetaRegionLocation(master.getZooKeeper()));
      }
      if (pair == null) {
        throw new UnknownRegionException(Bytes.toString(regionName));
      }

      RegionInfo hri = pair.getFirst();
      if (master.cpHost != null) {
        master.cpHost.preUnassign(hri, force);
      }
      LOG.debug(master.getClientIdAuditPrefix() + " unassign " + hri.getRegionNameAsString()
          + " in current location if it is online and reassign.force=" + force);
      master.getAssignmentManager().unassign(hri);
      if (master.cpHost != null) {
        master.cpHost.postUnassign(hri, force);
      }

      return urr;
    } catch (IOException ioe) {
      throw new ServiceException(ioe);
    }
  }

};