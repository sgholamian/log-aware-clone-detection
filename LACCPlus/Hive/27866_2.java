//,temp,MetaStoreDirectSql.java,2739,2821,temp,MetaStoreDirectSql.java,2653,2731
//,3
public class xxx {
  private void dropPartitionsByPartitionIds(List<Long> partitionIdList) throws MetaException {
    String queryText;
    if (partitionIdList.isEmpty()) {
      return;
    }

    String partitionIds = getIdListForIn(partitionIdList);

    // Get the corresponding SD_ID-s, CD_ID-s, SERDE_ID-s
    queryText =
        "SELECT " + SDS + ".\"SD_ID\", " + SDS + ".\"CD_ID\", " + SDS + ".\"SERDE_ID\" "
            + "from " + SDS + " "
            + "INNER JOIN " + PARTITIONS + " ON " + PARTITIONS + ".\"SD_ID\" = " + SDS + ".\"SD_ID\" "
            + "WHERE " + PARTITIONS + ".\"PART_ID\" in (" + partitionIds + ")";

    Query query = pm.newQuery("javax.jdo.query.SQL", queryText);
    List<Object[]> sqlResult = MetastoreDirectSqlUtils
        .ensureList(executeWithArray(query, null, queryText));

    List<Object> sdIdList = new ArrayList<>(partitionIdList.size());
    List<Object> columnDescriptorIdList = new ArrayList<>(1);
    List<Object> serdeIdList = new ArrayList<>(partitionIdList.size());

    if (!sqlResult.isEmpty()) {
      for (Object[] fields : sqlResult) {
        sdIdList.add(MetastoreDirectSqlUtils.extractSqlLong(fields[0]));
        Long colId = MetastoreDirectSqlUtils.extractSqlLong(fields[1]);
        if (!columnDescriptorIdList.contains(colId)) {
          columnDescriptorIdList.add(colId);
        }
        serdeIdList.add(MetastoreDirectSqlUtils.extractSqlLong(fields[2]));
      }
    }
    query.closeAll();

    try {
      // Drop privileges
      queryText = "delete from " + PART_PRIVS + " where \"PART_ID\" in (" + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop column level privileges
      queryText = "delete from " + PART_COL_PRIVS + " where \"PART_ID\" in (" + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop partition statistics
      queryText = "delete from " + PART_COL_STATS + " where \"PART_ID\" in (" + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the partition params
      queryText = "delete from " + PARTITION_PARAMS + " where \"PART_ID\" in ("
          + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the partition key vals
      queryText = "delete from " + PARTITION_KEY_VALS + " where \"PART_ID\" in ("
          + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the partitions
      queryText = "delete from " + PARTITIONS + " where \"PART_ID\" in (" + partitionIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();
    } catch (SQLException sqlException) {
      LOG.warn("SQL error executing query while dropping partition", sqlException);
      throw new MetaException("Encountered error while dropping partitions.");
    }
    dropStorageDescriptors(sdIdList);
    Deadline.checkTimeout();

    dropSerdes(serdeIdList);
    Deadline.checkTimeout();

    dropDanglingColumnDescriptors(columnDescriptorIdList);
  }

};