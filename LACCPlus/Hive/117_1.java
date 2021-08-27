//,temp,ObjectStore.java,10248,10270,temp,ObjectStore.java,693,713
//,3
public class xxx {
  @Override
  public List<String> getAllTokenIdentifiers() {
    LOG.debug("Begin executing getAllTokenIdentifiers");
    boolean committed = false;
    Query query = null;
    List<String> tokenIdents = new ArrayList<>();

    try {
      openTransaction();
      query = pm.newQuery(MDelegationToken.class);
      List<MDelegationToken> tokens = (List<MDelegationToken>) query.execute();
      pm.retrieveAll(tokens);
      committed = commitTransaction();

      for (MDelegationToken token : tokens) {
        tokenIdents.add(token.getTokenIdentifier());
      }
      return tokenIdents;
    } finally {
      LOG.debug("Done executing getAllTokenIdentifers with status : {}", committed);
      rollbackAndCleanup(committed, query);
    }
  }

};