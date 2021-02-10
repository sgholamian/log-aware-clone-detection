//,temp,FileSystemRMStateStore.java,942,971,temp,ZKRMStateStore.java,532,557
//,3
public class xxx {
  private synchronized void loadRMAppState(RMState rmState) throws Exception {
    List<String> childNodes = getChildren(rmAppRoot);
    for (String childNodeName : childNodes) {
      String childNodePath = getNodePath(rmAppRoot, childNodeName);
      byte[] childData = getData(childNodePath);
      if (childNodeName.startsWith(ApplicationId.appIdStrPrefix)) {
        // application
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading application from znode: " + childNodeName);
        }
        ApplicationId appId = ConverterUtils.toApplicationId(childNodeName);
        ApplicationStateDataPBImpl appState =
            new ApplicationStateDataPBImpl(
                ApplicationStateDataProto.parseFrom(childData));
        if (!appId.equals(
            appState.getApplicationSubmissionContext().getApplicationId())) {
          throw new YarnRuntimeException("The child node name is different " +
              "from the application id");
        }
        rmState.appState.put(appId, appState);
        loadApplicationAttemptState(appState, appId);
      } else {
        LOG.info("Unknown child node with name: " + childNodeName);
      }
    }
  }

};