//,temp,ZookeeperFederationStateStore.java,378,398,temp,MemoryFederationStateStore.java,269,282
//,3
public class xxx {
  @Override
  public GetSubClusterPolicyConfigurationResponse getPolicyConfiguration(
      GetSubClusterPolicyConfigurationRequest request) throws YarnException {

    FederationPolicyStoreInputValidator.validate(request);
    String queue = request.getQueue();
    if (!policies.containsKey(queue)) {
      LOG.warn("Policy for queue: {} does not exist.", queue);
      return null;
    }

    return GetSubClusterPolicyConfigurationResponse
        .newInstance(policies.get(queue));
  }

};