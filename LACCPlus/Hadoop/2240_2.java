//,temp,CancelCommand.java,91,105,temp,ExecuteCommand.java,94,111
//,3
public class xxx {
  private void submitPlan(final String planFile, final String planData,
                          boolean skipDateCheck)
          throws IOException {
    Preconditions.checkNotNull(planData);
    NodePlan plan = NodePlan.parseJson(planData);
    String dataNodeAddress = plan.getNodeName() + ":" + plan.getPort();
    Preconditions.checkNotNull(dataNodeAddress);
    ClientDatanodeProtocol dataNode = getDataNodeProxy(dataNodeAddress);
    String planHash = DigestUtils.shaHex(planData);
    try {
      dataNode.submitDiskBalancerPlan(planHash, DiskBalancerCLI.PLAN_VERSION,
                                      planFile, planData, skipDateCheck);
    } catch (DiskBalancerException ex) {
      LOG.error("Submitting plan on  {} failed. Result: {}, Message: {}",
          plan.getNodeName(), ex.getResult().toString(), ex.getMessage());
      throw ex;
    }
  }

};