//,temp,ShellBasedIdMapping.java,669,685,temp,ShellBasedIdMapping.java,651,667
//,2
public class xxx {
  synchronized public String getGroupName(int gid, String unknown) {
    checkAndUpdateMaps();
    String gname = gidNameMap.get(gid);
    if (gname == null) {
      try {
        updateMapIncr(gid, true);
      } catch (Exception e) {        
      }
      gname = gidNameMap.get(gid);
      if (gname == null) {
        LOG.warn("Can't find group name for gid " + gid
            + ". Use default group name " + unknown);
        gname = unknown;
      }
    }
    return gname;
  }

};