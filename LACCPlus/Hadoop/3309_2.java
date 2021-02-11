//,temp,ShellBasedIdMapping.java,701,711,temp,ShellBasedIdMapping.java,688,698
//,2
public class xxx {
  public int getUidAllowingUnknown(String user) {
    checkAndUpdateMaps();
    int uid;
    try {
      uid = getUid(user);
    } catch (IOException e) {
      uid = user.hashCode();
      LOG.info("Can't map user " + user + ". Use its string hashcode:" + uid);
    }
    return uid;
  }

};