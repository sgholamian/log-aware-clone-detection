//,temp,NMLeveldbStateStoreService.java,715,730,temp,NMLeveldbStateStoreService.java,508,525
//,3
public class xxx {
  @Override
  public void storeApplication(ApplicationId appId,
      ContainerManagerApplicationProto p) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("storeApplication: appId=" + appId
          + ", proto=" + p);
    }

    String key = APPLICATIONS_KEY_PREFIX + appId;
    try {
      db.put(bytes(key), p.toByteArray());
    } catch (DBException e) {
      markStoreUnHealthy(e);
      throw new IOException(e);
    }
  }

};