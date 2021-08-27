//,temp,HiveServer2.java,779,792,temp,HiveServer2.java,761,777
//,3
public class xxx {
    @Override
    public void isLeader() {
      LOG.info("HS2 instance {} became the LEADER. Starting/Reconnecting tez sessions..", hiveServer2.serviceUri);
      hiveServer2.isLeader.set(true);
      if (parentSession != null) {
        SessionState.setCurrentSessionState(parentSession);
      }
      hiveServer2.startOrReconnectTezSessions();
      LOG.info("Started/Reconnected tez sessions.");
      hiveServer2.allowClientSessions();

      // resolve futures used for testing
      if (HiveConf.getBoolVar(hiveServer2.getHiveConf(), ConfVars.HIVE_IN_TEST)) {
        hiveServer2.isLeaderTestFuture.set(true);
        hiveServer2.resetNotLeaderTestFuture();
      }
    }

};