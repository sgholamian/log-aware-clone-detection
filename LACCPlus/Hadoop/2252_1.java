//,temp,ZookeeperFederationStateStore.java,378,398,temp,MemoryFederationStateStore.java,269,282
//,3
public class xxx {
  @Override
  public GetSubClusterPolicyConfigurationResponse getPolicyConfiguration(
      GetSubClusterPolicyConfigurationRequest request) throws YarnException {

    FederationPolicyStoreInputValidator.validate(request);
    String queue = request.getQueue();
    SubClusterPolicyConfiguration policy = null;
    try {
      policy = getPolicy(queue);
    } catch (Exception e) {
      String errMsg = "Cannot get policy: " + e.getMessage();
      FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
    }

    if (policy == null) {
      LOG.warn("Policy for queue: {} does not exist.", queue);
      return null;
    }
    return GetSubClusterPolicyConfigurationResponse
        .newInstance(policy);
  }

};