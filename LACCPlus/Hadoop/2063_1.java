//,temp,ZKRMStateStore.java,495,530,temp,ZKRMStateStore.java,450,479
//,3
public class xxx {
  private void loadRMDelegationTokenState(RMState rmState) throws Exception {
    List<String> childNodes =
        getChildren(delegationTokensRootPath);
    for (String childNodeName : childNodes) {
      String childNodePath =
          getNodePath(delegationTokensRootPath, childNodeName);
      byte[] childData = getData(childNodePath);

      if (childData == null) {
        LOG.warn("Content of " + childNodePath + " is broken.");
        continue;
      }

      ByteArrayInputStream is = new ByteArrayInputStream(childData);
      DataInputStream fsIn = new DataInputStream(is);

      try {
        if (childNodeName.startsWith(DELEGATION_TOKEN_PREFIX)) {
          RMDelegationTokenIdentifierData identifierData =
              new RMDelegationTokenIdentifierData();
          identifierData.readFields(fsIn);
          RMDelegationTokenIdentifier identifier =
              identifierData.getTokenIdentifier();
          long renewDate = identifierData.getRenewDate();
          rmState.rmSecretManagerState.delegationTokenState.put(identifier,
              renewDate);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded RMDelegationTokenIdentifier: " + identifier
                + " renewDate=" + renewDate);
          }
        }
      } finally {
        is.close();
      }
    }
  }

};