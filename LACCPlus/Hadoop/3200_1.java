//,temp,ZKDelegationTokenSecretManager.java,879,904,temp,ZKDelegationTokenSecretManager.java,740,774
//,3
public class xxx {
  private void addOrUpdateToken(TokenIdent ident,
      DelegationTokenInformation info, boolean isUpdate) throws Exception {
    String nodeCreatePath =
        getNodePath(ZK_DTSM_TOKENS_ROOT, DELEGATION_TOKEN_PREFIX
            + ident.getSequenceNumber());

    try (ByteArrayOutputStream tokenOs = new ByteArrayOutputStream();
         DataOutputStream tokenOut = new DataOutputStream(tokenOs)) {
      ident.write(tokenOut);
      tokenOut.writeLong(info.getRenewDate());
      tokenOut.writeInt(info.getPassword().length);
      tokenOut.write(info.getPassword());
      if (LOG.isDebugEnabled()) {
        LOG.debug((isUpdate ? "Updating " : "Storing ")
            + "ZKDTSMDelegationToken_" +
            ident.getSequenceNumber());
      }
      if (isUpdate) {
        zkClient.setData().forPath(nodeCreatePath, tokenOs.toByteArray())
            .setVersion(-1);
      } else {
        zkClient.create().withMode(CreateMode.PERSISTENT)
            .forPath(nodeCreatePath, tokenOs.toByteArray());
      }
    }
  }

};