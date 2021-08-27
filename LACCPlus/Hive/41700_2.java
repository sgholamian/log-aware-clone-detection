//,temp,ReloadProcessor.java,33,43,temp,DbTxnManager.java,189,198
//,3
public class xxx {
  IMetaStoreClient getMS() throws LockException {
    try {
      return Hive.get(conf).getMSC();
    }
    catch(HiveException|MetaException e) {
      String msg = "Unable to reach Hive Metastore: " + e.getMessage();
      LOG.error(msg, e);
      throw new LockException(e);
    }
  }

};