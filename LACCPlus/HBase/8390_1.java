//,temp,HMaster.java,3744,3750,temp,HMaster.java,3711,3715
//,3
public class xxx {
  @Override
  public long updateReplicationPeerConfig(String peerId, ReplicationPeerConfig peerConfig)
      throws ReplicationException, IOException {
    LOG.info(getClientIdAuditPrefix() + " update replication peer config, id=" + peerId +
      ", config=" + peerConfig);
    return executePeerProcedure(new UpdatePeerConfigProcedure(peerId, peerConfig));
  }

};