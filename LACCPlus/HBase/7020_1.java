//,temp,EnablePeerProcedure.java,65,72,temp,DisablePeerProcedure.java,65,72
//,2
public class xxx {
  @Override
  protected void postPeerModification(MasterProcedureEnv env) throws IOException {
    LOG.info("Successfully enabled peer {}", peerId);
    MasterCoprocessorHost cpHost = env.getMasterCoprocessorHost();
    if (cpHost != null) {
      cpHost.postEnableReplicationPeer(peerId);
    }
  }

};