//,temp,HMSHandler.java,9846,9856,temp,HMSHandler.java,9792,9802
//,2
public class xxx {
  @Override
  public WMDropPoolResponse drop_wm_pool(WMDropPoolRequest request)
      throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
    try {
      getMS().dropWMPool(request.getResourcePlanName(), request.getPoolPath(), request.getNs());
      return new WMDropPoolResponse();
    } catch (MetaException e) {
      LOG.error("Exception while trying to drop WMPool", e);
      throw e;
    }
  }

};