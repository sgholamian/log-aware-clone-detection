//,temp,HistoryServerFileSystemStateStoreService.java,176,199,temp,HistoryServerLeveldbStateStoreService.java,210,234
//,3
public class xxx {
  @Override
  public void storeTokenMasterKey(DelegationKey key) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing master key " + key.getKeyId());
    }

    Path keyPath = new Path(tokenKeysStatePath,
        TOKEN_MASTER_KEY_FILE_PREFIX + key.getKeyId());
    if (fs.exists(keyPath)) {
      throw new FileAlreadyExistsException(keyPath + " already exists");
    }

    ByteArrayOutputStream memStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(memStream);
    try {
      key.write(dataStream);
      dataStream.close();
      dataStream = null;
    } finally {
      IOUtils.cleanupWithLogger(LOG, dataStream);
    }

    createNewFile(keyPath, memStream.toByteArray());
  }

};