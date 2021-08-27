//,temp,HiveServer2.java,779,792,temp,HiveServer2.java,761,777
//,3
public class xxx {
    @Override
    public void notLeader() {
      LOG.info("HS2 instance {} LOST LEADERSHIP. Stopping/Disconnecting tez sessions..", hiveServer2.serviceUri);
      hiveServer2.isLeader.set(false);
      hiveServer2.closeAndDisallowHiveSessions();
      hiveServer2.stopOrDisconnectTezSessions();
      LOG.info("Stopped/Disconnected tez sessions.");

      // resolve futures used for testing
      if (HiveConf.getBoolVar(hiveServer2.getHiveConf(), ConfVars.HIVE_IN_TEST)) {
        hiveServer2.notLeaderTestFuture.set(true);
        hiveServer2.resetIsLeaderTestFuture();
      }
    }

};