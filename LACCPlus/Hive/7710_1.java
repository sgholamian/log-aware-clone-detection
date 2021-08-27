//,temp,ObjectStore.java,8583,8614,temp,ObjectStore.java,8415,8440
//,3
public class xxx {
  @Override
  public boolean isPartitionMarkedForEvent(String catName, String dbName, String tblName,
      Map<String, String> partName, PartitionEventType evtType) throws UnknownTableException,
      MetaException, InvalidPartitionException, UnknownPartitionException {
    boolean success = false;
    Query query = null;

    try {
      LOG.debug("Begin Executing isPartitionMarkedForEvent");

      openTransaction();
      query = pm.newQuery(MPartitionEvent.class,
              "dbName == t1 && tblName == t2 && partName == t3 && eventType == t4 && catalogName == t5");
      query
          .declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3, int t4," +
              "java.lang.String t5");
      Table tbl = getTable(catName, dbName, tblName, null); // Make sure dbName and tblName are valid.
      if (null == tbl) {
        throw new UnknownTableException("Table: " + tblName + " is not found.");
      }
      Collection<MPartitionEvent> partEvents =
          (Collection<MPartitionEvent>) query.executeWithArray(dbName, tblName,
              getPartitionStr(tbl, partName), evtType.getValue(), catName);
      pm.retrieveAll(partEvents);
      success = commitTransaction();

      LOG.debug("Done executing isPartitionMarkedForEvent");
      return partEvents != null && !partEvents.isEmpty();
    } finally {
      rollbackAndCleanup(success, query);
    }
  }

};