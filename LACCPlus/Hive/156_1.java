//,temp,TezSessionPool.java,339,352,temp,TezSessionPool.java,326,337
//,3
public class xxx {
    @Override
    public void onRemove(TezAmInstance serviceInstance, int ephSeqVersion) {
      String sessionId = serviceInstance.getSessionId();
      // For now, we don't take any pool action. In future, we might restore the session based
      // on this and get rid of the logic outside of the pool that replaces/reopens/etc.
      SessionType session = bySessionId.get(sessionId);
      if (session != null) {
        LOG.info("AM for " + sessionId + ", v." + ephSeqVersion
            + " has unregistered; updating [" + session + "]");
        session.updateFromRegistry(null, ephSeqVersion);
      } else {
        LOG.warn("AM for an unknown " + sessionId + " has unregistered; ignoring");
      }
    }

};