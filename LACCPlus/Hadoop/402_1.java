//,temp,FileSystemRMStateStore.java,942,971,temp,ZKRMStateStore.java,532,557
//,3
public class xxx {
    @Override
    public void processChildNode(String appDirName, String childNodeName,
        byte[] childData)
        throws com.google.protobuf.InvalidProtocolBufferException {
      if (childNodeName.startsWith(ApplicationId.appIdStrPrefix)) {
        // application
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading application from node: " + childNodeName);
        }
        ApplicationStateDataPBImpl appState =
            new ApplicationStateDataPBImpl(
                ApplicationStateDataProto.parseFrom(childData));
        ApplicationId appId =
            appState.getApplicationSubmissionContext().getApplicationId();
        rmState.appState.put(appId, appState);
      } else if (childNodeName.startsWith(
          ApplicationAttemptId.appAttemptIdStrPrefix)) {
        // attempt
        if (LOG.isDebugEnabled()) {
          LOG.debug("Loading application attempt from node: "
              + childNodeName);
        }
        ApplicationAttemptStateDataPBImpl attemptState =
            new ApplicationAttemptStateDataPBImpl(
                ApplicationAttemptStateDataProto.parseFrom(childData));
        attempts.add(attemptState);
      } else {
        LOG.info("Unknown child node with name: " + childNodeName);
      }
    }

};