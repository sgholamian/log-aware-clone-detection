//,temp,LazyPersistTestCase.java,132,144,temp,DFSTestUtil.java,1582,1601
//,3
public class xxx {
  public static boolean verifyFileReplicasOnStorageType(FileSystem fs,
    DFSClient client, Path path, StorageType storageType) throws IOException {
    if (!fs.exists(path)) {
      LOG.info("verifyFileReplicasOnStorageType: file " + path + "does not exist");
      return false;
    }
    long fileLength = client.getFileInfo(path.toString()).getLen();
    LocatedBlocks locatedBlocks =
      client.getLocatedBlocks(path.toString(), 0, fileLength);
    for (LocatedBlock locatedBlock : locatedBlocks.getLocatedBlocks()) {
      if (locatedBlock.getStorageTypes()[0] != storageType) {
        LOG.info("verifyFileReplicasOnStorageType: for file " + path +
            ". Expect blk" + locatedBlock +
          " on Type: " + storageType + ". Actual Type: " +
          locatedBlock.getStorageTypes()[0]);
        return false;
      }
    }
    return true;
  }

};