//,temp,FsDatasetImpl.java,1266,1285,temp,FsDatasetImpl.java,824,846
//,3
public class xxx {
  private void bumpReplicaGS(ReplicaInfo replicaInfo, 
      long newGS) throws IOException { 
    long oldGS = replicaInfo.getGenerationStamp();
    File oldmeta = replicaInfo.getMetaFile();
    replicaInfo.setGenerationStamp(newGS);
    File newmeta = replicaInfo.getMetaFile();

    // rename meta file to new GS
    if (LOG.isDebugEnabled()) {
      LOG.debug("Renaming " + oldmeta + " to " + newmeta);
    }
    try {
      NativeIO.renameTo(oldmeta, newmeta);
    } catch (IOException e) {
      replicaInfo.setGenerationStamp(oldGS); // restore old GS
      throw new IOException("Block " + replicaInfo + " reopen failed. " +
                            " Unable to move meta file  " + oldmeta +
                            " to " + newmeta, e);
    }
  }

};