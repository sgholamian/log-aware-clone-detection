//,temp,HiveMetaStore.java,715,722,temp,HiveMetaStore.java,696,703
//,2
public class xxx {
  private static void startCompactorInitiator(Configuration conf) throws Exception {
    if (MetastoreConf.getBoolVar(conf, ConfVars.COMPACTOR_INITIATOR_ON)) {
      MetaStoreThread initiator =
          instantiateThread("org.apache.hadoop.hive.ql.txn.compactor.Initiator");
      initializeAndStartThread(initiator, conf);
      LOG.info("This HMS instance will act as a Compactor Initiator.");
    }
  }

};