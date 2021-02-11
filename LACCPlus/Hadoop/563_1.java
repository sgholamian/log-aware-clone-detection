//,temp,ShellBasedIdMapping.java,701,711,temp,ShellBasedIdMapping.java,688,698
//,2
public class xxx {
  public int getGidAllowingUnknown(String group) {
    checkAndUpdateMaps();
    int gid;
    try {
      gid = getGid(group);
    } catch (IOException e) {
      gid = group.hashCode();
      LOG.info("Can't map group " + group + ". Use its string hashcode:" + gid);
    }
    return gid;
  }

};