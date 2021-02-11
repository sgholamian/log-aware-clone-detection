//,temp,TestHBaseSaslRpcClient.java,276,284,temp,TestHBaseSaslRpcClient.java,255,263
//,3
public class xxx {
  private boolean assertSuccessCreationKerberosPrincipal(String principal) {
    HBaseSaslRpcClient rpcClient = null;
    try {
      rpcClient = createSaslRpcClientForKerberos(principal);
    } catch(Exception ex) {
      LOG.error(ex.getMessage(), ex);
    }
    return rpcClient != null;
  }

};