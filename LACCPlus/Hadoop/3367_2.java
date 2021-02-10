//,temp,ApiServiceClient.java,579,601,temp,ApiServiceClient.java,555,577
//,2
public class xxx {
  @Override
  public int actionUpgradeInstances(String appName, List<String> compInstances)
      throws IOException, YarnException {
    int result;
    Container[] toUpgrade = new Container[compInstances.size()];
    try {
      int idx = 0;
      for (String instanceName : compInstances) {
        Container container = new Container();
        container.setComponentInstanceName(instanceName);
        container.setState(ContainerState.UPGRADING);
        toUpgrade[idx++] = container;
      }
      String buffer = ServiceApiUtil.CONTAINER_JSON_SERDE.toJson(toUpgrade);
      ClientResponse response = getApiClient(getInstancesPath(appName))
          .put(ClientResponse.class, buffer);
      result = processResponse(response);
    } catch (Exception e) {
      LOG.error("Failed to upgrade component instance: ", e);
      result = EXIT_EXCEPTION_THROWN;
    }
    return result;
  }

};