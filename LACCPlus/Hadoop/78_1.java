//,temp,FsDatasetImpl.java,1218,1241,temp,FsDatasetImpl.java,1061,1097
//,3
public class xxx {
  @Override  // FsDatasetSpi
  public synchronized ReplicaHandler recoverAppend(
      ExtendedBlock b, long newGS, long expectedBlockLen) throws IOException {
    LOG.info("Recover failed append to " + b);

    ReplicaInfo replicaInfo = recoverCheck(b, newGS, expectedBlockLen);

    FsVolumeReference ref = replicaInfo.getVolume().obtainReference();
    ReplicaBeingWritten replica;
    try {
      // change the replica's state/gs etc.
      if (replicaInfo.getState() == ReplicaState.FINALIZED) {
        replica = append(b.getBlockPoolId(), (FinalizedReplica) replicaInfo,
                         newGS, b.getNumBytes());
      } else { //RBW
        bumpReplicaGS(replicaInfo, newGS);
        replica = (ReplicaBeingWritten) replicaInfo;
      }
    } catch (IOException e) {
      IOUtils.cleanup(null, ref);
      throw e;
    }
    return new ReplicaHandler(replica, ref);
  }

};