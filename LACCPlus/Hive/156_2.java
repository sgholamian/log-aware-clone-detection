//,temp,TezSessionPool.java,339,352,temp,TezSessionPool.java,326,337
//,3
public class xxx {
    @Override
    public void onUpdate(TezAmInstance si, int ephSeqVersion) {
      String sessionId = si.getSessionId();
      SessionType session = bySessionId.get(sessionId);
      if (session != null) {
        LOG.info("AM for " + sessionId + ", v." + ephSeqVersion + " has updated; updating ["
            + session + "] with an endpoint at " + si.getPluginPort());
        session.updateFromRegistry(si, ephSeqVersion);
      } else {
        LOG.warn("AM for an unknown " + sessionId + " has updated; ignoring");
      }
    }

};