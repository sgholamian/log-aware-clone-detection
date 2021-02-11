//,temp,HMaster.java,3744,3750,temp,HMaster.java,3711,3715
//,3
public class xxx {
  @Override
  public long removeReplicationPeer(String peerId) throws ReplicationException, IOException {
    LOG.info(getClientIdAuditPrefix() + " removing replication peer, id=" + peerId);
    return executePeerProcedure(new RemovePeerProcedure(peerId));
  }

};