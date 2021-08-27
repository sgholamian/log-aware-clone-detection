//,temp,ObjectStore.java,10322,10343,temp,ObjectStore.java,5212,5247
//,3
public class xxx {
  private void removeUnusedColumnDescriptor(MColumnDescriptor oldCD) {
    if (oldCD == null) {
      return;
    }

    boolean success = false;
    Query query = null;

    try {
      openTransaction();
      LOG.debug("execute removeUnusedColumnDescriptor");

      query = pm.newQuery("select count(1) from " +
        "org.apache.hadoop.hive.metastore.model.MStorageDescriptor where (this.cd == inCD)");
      query.declareParameters("MColumnDescriptor inCD");
      long count = ((Long)query.execute(oldCD)).longValue();

      //if no other SD references this CD, we can throw it out.
      if (count == 0) {
        // First remove any constraints that may be associated with this CD
        query = pm.newQuery(MConstraint.class, "parentColumn == inCD || childColumn == inCD");
        query.declareParameters("MColumnDescriptor inCD");
        List<MConstraint> mConstraintsList = (List<MConstraint>) query.execute(oldCD);
        if (CollectionUtils.isNotEmpty(mConstraintsList)) {
          pm.deletePersistentAll(mConstraintsList);
        }
        // Finally remove CD
        pm.retrieve(oldCD);
        pm.deletePersistent(oldCD);
      }
      success = commitTransaction();
      LOG.debug("successfully deleted a CD in removeUnusedColumnDescriptor");
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};