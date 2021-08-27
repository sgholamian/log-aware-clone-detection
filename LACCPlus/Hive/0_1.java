//,temp,ObjectStore.java,13621,13635,temp,ObjectStore.java,3258,3274
//,3
public class xxx {
  @Override
  public void addRuntimeStat(RuntimeStat stat) throws MetaException {
    LOG.debug("runtimeStat: {}", stat);
    MRuntimeStat mStat = MRuntimeStat.fromThrift(stat);
    boolean committed = false;
    openTransaction();
    try {
      pm.makePersistent(mStat);
      committed = commitTransaction();
    } finally {
      if (!committed) {
        rollbackTransaction();
      }
    }
  }

};