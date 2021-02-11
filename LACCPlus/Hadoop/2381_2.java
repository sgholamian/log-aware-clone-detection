//,temp,NMLeveldbStateStoreService.java,844,864,temp,NMLeveldbStateStoreService.java,823,842
//,3
public class xxx {
  private List<LocalizedResourceProto> loadCompletedResources(
      LeveldbIterator iter, String keyPrefix) throws IOException {
    List<LocalizedResourceProto> rsrcs =
        new ArrayList<LocalizedResourceProto>();
    while (iter.hasNext()) {
      Entry<byte[],byte[]> entry = iter.peekNext();
      String key = asString(entry.getKey());
      if (!key.startsWith(keyPrefix)) {
        break;
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("Loading completed resource from " + key);
      }
      rsrcs.add(LocalizedResourceProto.parseFrom(entry.getValue()));
      iter.next();
    }

    return rsrcs;
  }

};