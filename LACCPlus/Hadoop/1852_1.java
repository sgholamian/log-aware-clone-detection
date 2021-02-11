//,temp,LazyPersistTestCase.java,132,144,temp,DFSTestUtil.java,1582,1601
//,3
public class xxx {
  protected final LocatedBlocks ensureFileReplicasOnStorageType(
      Path path, StorageType storageType) throws IOException {
    // Ensure that returned block locations returned are correct!
    LOG.info("Ensure path: " + path + " is on StorageType: " + storageType);
    assertThat(fs.exists(path), is(true));
    long fileLength = client.getFileInfo(path.toString()).getLen();
    LocatedBlocks locatedBlocks =
        client.getLocatedBlocks(path.toString(), 0, fileLength);
    for (LocatedBlock locatedBlock : locatedBlocks.getLocatedBlocks()) {
      assertThat(locatedBlock.getStorageTypes()[0], is(storageType));
    }
    return locatedBlocks;
  }

};