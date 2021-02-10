//,temp,TestSaslRPC.java,854,864,temp,TestSaslRPC.java,843,852
//,3
public class xxx {
  private String getAuthMethod(
      final AuthMethod clientAuth,
      final AuthMethod serverAuth,
      final UseToken tokenType) throws Exception {
    try {
      return internalGetAuthMethod(clientAuth, serverAuth, tokenType);
    } catch (Exception e) {
      LOG.warn("Auth method failure", e);
      return e.toString();
    }
  }

};