//,temp,CancelCommand.java,91,105,temp,ExecuteCommand.java,94,111
//,3
public class xxx {
  private void cancelPlan(String planData) throws IOException {
    Preconditions.checkNotNull(planData);
    NodePlan plan = NodePlan.parseJson(planData);
    String dataNodeAddress = plan.getNodeName() + ":" + plan.getPort();
    Preconditions.checkNotNull(dataNodeAddress);
    ClientDatanodeProtocol dataNode = getDataNodeProxy(dataNodeAddress);
    String planHash = DigestUtils.shaHex(planData);
    try {
      dataNode.cancelDiskBalancePlan(planHash);
    } catch (DiskBalancerException ex) {
      LOG.error("Cancelling plan on  {} failed. Result: {}, Message: {}",
          plan.getNodeName(), ex.getResult().toString(), ex.getMessage());
      throw ex;
    }
  }

};