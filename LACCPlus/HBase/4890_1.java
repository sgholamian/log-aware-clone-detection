//,temp,TestHBaseSaslRpcClient.java,276,284,temp,TestHBaseSaslRpcClient.java,255,263
//,3
public class xxx {
  private boolean assertSuccessCreationSimplePrincipal(String principal, String password) {
    HBaseSaslRpcClient rpcClient = null;
    try {
      rpcClient = createSaslRpcClientSimple(principal, password);
    } catch(Exception ex) {
      LOG.error(ex.getMessage(), ex);
    }
    return rpcClient != null;
  }

};