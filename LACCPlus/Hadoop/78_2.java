//,temp,FsDatasetImpl.java,1218,1241,temp,FsDatasetImpl.java,1061,1097
//,3
public class xxx {
  @Override  // FsDatasetSpi
  public synchronized ReplicaHandler append(ExtendedBlock b,
      long newGS, long expectedBlockLen) throws IOException {
    // If the block was successfully finalized because all packets
    // were successfully processed at the Datanode but the ack for
    // some of the packets were not received by the client. The client 
    // re-opens the connection and retries sending those packets.
    // The other reason is that an "append" is occurring to this block.
    
    // check the validity of the parameter
    if (newGS < b.getGenerationStamp()) {
      throw new IOException("The new generation stamp " + newGS + 
          " should be greater than the replica " + b + "'s generation stamp");
    }
    ReplicaInfo replicaInfo = getReplicaInfo(b);
    LOG.info("Appending to " + replicaInfo);
    if (replicaInfo.getState() != ReplicaState.FINALIZED) {
      throw new ReplicaNotFoundException(
          ReplicaNotFoundException.UNFINALIZED_REPLICA + b);
    }
    if (replicaInfo.getNumBytes() != expectedBlockLen) {
      throw new IOException("Corrupted replica " + replicaInfo + 
          " with a length of " + replicaInfo.getNumBytes() + 
          " expected length is " + expectedBlockLen);
    }

    FsVolumeReference ref = replicaInfo.getVolume().obtainReference();
    ReplicaBeingWritten replica = null;
    try {
      replica = append(b.getBlockPoolId(), (FinalizedReplica)replicaInfo, newGS,
          b.getNumBytes());
    } catch (IOException e) {
      IOUtils.cleanup(null, ref);
      throw e;
    }
    return new ReplicaHandler(replica, ref);
  }

};