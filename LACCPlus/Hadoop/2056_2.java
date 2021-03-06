//,temp,DataStorage.java,254,293,temp,BlockPoolSliceStorage.java,144,186
//,3
public class xxx {
  private StorageDirectory loadStorageDirectory(DataNode datanode,
      NamespaceInfo nsInfo, File dataDir, StartupOption startOpt) throws IOException {
    StorageDirectory sd = new StorageDirectory(dataDir, null, true);
    try {
      StorageState curState = sd.analyzeStorage(startOpt, this);
      // sd is locked but not opened
      switch (curState) {
      case NORMAL:
        break;
      case NON_EXISTENT:
        LOG.info("Block pool storage directory " + dataDir + " does not exist");
        throw new IOException("Storage directory " + dataDir
            + " does not exist");
      case NOT_FORMATTED: // format
        LOG.info("Block pool storage directory " + dataDir
            + " is not formatted for " + nsInfo.getBlockPoolID());
        LOG.info("Formatting ...");
        format(sd, nsInfo);
        break;
      default:  // recovery part is common
        sd.doRecover(curState);
      }

      // 2. Do transitions
      // Each storage directory is treated individually.
      // During startup some of them can upgrade or roll back
      // while others could be up-to-date for the regular startup.
      doTransition(datanode, sd, nsInfo, startOpt);
      if (getCTime() != nsInfo.getCTime()) {
        throw new IOException(
            "Data-node and name-node CTimes must be the same.");
      }

      // 3. Update successfully loaded storage.
      setServiceLayoutVersion(getServiceLayoutVersion());
      writeProperties(sd);

      return sd;
    } catch (IOException ioe) {
      sd.unlock();
      throw ioe;
    }
  }

};