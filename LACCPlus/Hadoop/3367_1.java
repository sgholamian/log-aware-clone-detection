//,temp,ApiServiceClient.java,579,601,temp,ApiServiceClient.java,555,577
//,2
public class xxx {
  @Override
  public int actionUpgradeComponents(String appName, List<String> components)
      throws IOException, YarnException {
    int result;
    Component[] toUpgrade = new Component[components.size()];
    try {
      int idx = 0;
      for (String compName : components) {
        Component component = new Component();
        component.setName(compName);
        component.setState(ComponentState.UPGRADING);
        toUpgrade[idx++] = component;
      }
      String buffer = ServiceApiUtil.COMP_JSON_SERDE.toJson(toUpgrade);
      ClientResponse response = getApiClient(getComponentsPath(appName))
          .put(ClientResponse.class, buffer);
      result = processResponse(response);
    } catch (Exception e) {
      LOG.error("Failed to upgrade components: ", e);
      result = EXIT_EXCEPTION_THROWN;
    }
    return result;
  }

};