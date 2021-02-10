//,temp,ZKDelegationTokenSecretManager.java,654,671,temp,ZKDelegationTokenSecretManager.java,615,631
//,3
public class xxx {
  @Override
  protected DelegationTokenInformation getTokenInfo(TokenIdent ident) {
    // First check if I have this..
    DelegationTokenInformation tokenInfo = currentTokens.get(ident);
    // Then query ZK
    if (tokenInfo == null) {
      try {
        tokenInfo = getTokenInfoFromZK(ident);
        if (tokenInfo != null) {
          currentTokens.put(ident, tokenInfo);
        }
      } catch (IOException e) {
        LOG.error("Error retrieving tokenInfo [" + ident.getSequenceNumber()
            + "] from ZK", e);
      }
    }
    return tokenInfo;
  }

};