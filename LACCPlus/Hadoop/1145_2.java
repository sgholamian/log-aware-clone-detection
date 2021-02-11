//,temp,ShellBasedIdMapping.java,669,685,temp,ShellBasedIdMapping.java,651,667
//,2
public class xxx {
  synchronized public String getUserName(int uid, String unknown) {
    checkAndUpdateMaps();
    String uname = uidNameMap.get(uid);
    if (uname == null) {
      try {
        updateMapIncr(uid, false);
      } catch (Exception e) {        
      }
      uname = uidNameMap.get(uid);
      if (uname == null) {     
        LOG.warn("Can't find user name for uid " + uid
            + ". Use default user name " + unknown);
        uname = unknown;
      }
    }
    return uname;
  }

};