//,temp,StateStoreZooKeeperImpl.java,212,258,temp,StateStoreFileBaseImpl.java,386,433
//,3
public class xxx {
  @Override
  public <T extends BaseRecord> int remove(
      Class<T> clazz, Query<T> query) throws IOException {
    verifyDriverReady();
    if (query == null) {
      return 0;
    }

    // Read the current data
    long start = monotonicNow();
    List<T> records = null;
    try {
      QueryResult<T> result = get(clazz);
      records = result.getRecords();
    } catch (IOException ex) {
      LOG.error("Cannot get existing records", ex);
      getMetrics().addFailure(monotonicNow() - start);
      return 0;
    }

    // Check the records to remove
    String znode = getZNodeForClass(clazz);
    List<T> recordsToRemove = filterMultiple(query, records);

    // Remove the records
    int removed = 0;
    for (T existingRecord : recordsToRemove) {
      LOG.info("Removing \"{}\"", existingRecord);
      try {
        String primaryKey = getPrimaryKey(existingRecord);
        String path = getNodePath(znode, primaryKey);
        if (zkManager.delete(path)) {
          removed++;
        } else {
          LOG.error("Did not remove \"{}\"", existingRecord);
        }
      } catch (Exception e) {
        LOG.error("Cannot remove \"{}\"", existingRecord, e);
        getMetrics().addFailure(monotonicNow() - start);
      }
    }
    long end = monotonicNow();
    if (removed > 0) {
      getMetrics().addRemove(end - start);
    }
    return removed;
  }

};