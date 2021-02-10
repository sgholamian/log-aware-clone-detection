//,temp,StateStoreZooKeeperImpl.java,212,258,temp,StateStoreFileBaseImpl.java,386,433
//,3
public class xxx {
  @Override
  public <T extends BaseRecord> int remove(Class<T> clazz, Query<T> query)
      throws StateStoreUnavailableException {
    verifyDriverReady();

    if (query == null) {
      return 0;
    }

    long start = Time.monotonicNow();
    StateStoreMetrics metrics = getMetrics();
    int removed = 0;
    // Get the current records
    try {
      final QueryResult<T> result = get(clazz);
      final List<T> existingRecords = result.getRecords();
      // Write all of the existing records except those to be removed
      final List<T> recordsToRemove = filterMultiple(query, existingRecords);
      boolean success = true;
      for (T recordToRemove : recordsToRemove) {
        String path = getPathForClass(clazz);
        String primaryKey = getPrimaryKey(recordToRemove);
        String recordToRemovePath = path + "/" + primaryKey;
        if (remove(recordToRemovePath)) {
          removed++;
        } else {
          LOG.error("Cannot remove record {}", recordToRemovePath);
          success = false;
        }
      }
      if (!success) {
        LOG.error("Cannot remove records {} query {}", clazz, query);
        if (metrics != null) {
          metrics.addFailure(monotonicNow() - start);
        }
      }
    } catch (IOException e) {
      LOG.error("Cannot remove records {} query {}", clazz, query, e);
      if (metrics != null) {
        metrics.addFailure(monotonicNow() - start);
      }
    }

    if (removed > 0 && metrics != null) {
      metrics.addRemove(monotonicNow() - start);
    }
    return removed;
  }

};