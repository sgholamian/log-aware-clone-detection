//,temp,MetaStoreDirectSql.java,2739,2821,temp,MetaStoreDirectSql.java,2653,2731
//,3
public class xxx {
  private void dropStorageDescriptors(List<Object> storageDescriptorIdList) throws MetaException {
    if (storageDescriptorIdList.isEmpty()) {
      return;
    }
    String queryText;
    String sdIds = getIdListForIn(storageDescriptorIdList);

    // Get the corresponding SKEWED_STRING_LIST_ID data
    queryText =
        "select " + SKEWED_VALUES + ".\"STRING_LIST_ID_EID\" "
            + "from " + SKEWED_VALUES + " "
            + "WHERE " + SKEWED_VALUES + ".\"SD_ID_OID\" in  (" + sdIds + ")";

    Query query = pm.newQuery("javax.jdo.query.SQL", queryText);
    List<Object[]> sqlResult = MetastoreDirectSqlUtils
        .ensureList(executeWithArray(query, null, queryText));

    List<Object> skewedStringListIdList = new ArrayList<>(0);

    if (!sqlResult.isEmpty()) {
      for (Object[] fields : sqlResult) {
        skewedStringListIdList.add(MetastoreDirectSqlUtils.extractSqlLong(fields[0]));
      }
    }
    query.closeAll();

    String skewedStringListIds = getIdListForIn(skewedStringListIdList);

    try {
      // Drop the SD params
      queryText = "delete from " + SD_PARAMS + " where \"SD_ID\" in (" + sdIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the sort cols
      queryText = "delete from " + SORT_COLS + " where \"SD_ID\" in (" + sdIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the bucketing cols
      queryText = "delete from " + BUCKETING_COLS + " where \"SD_ID\" in (" + sdIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the skewed string lists
      if (skewedStringListIdList.size() > 0) {
        // Drop the skewed string value loc map
        queryText = "delete from " + SKEWED_COL_VALUE_LOC_MAP + " where \"SD_ID\" in ("
            + sdIds + ")";
        executeNoResult(queryText);
        Deadline.checkTimeout();

        // Drop the skewed values
        queryText = "delete from " + SKEWED_VALUES + " where \"SD_ID_OID\" in (" + sdIds + ")";
        executeNoResult(queryText);
        Deadline.checkTimeout();

        // Drop the skewed string list values
        queryText = "delete from " + SKEWED_STRING_LIST_VALUES + " where \"STRING_LIST_ID\" in ("
            + skewedStringListIds + ")";
        executeNoResult(queryText);
        Deadline.checkTimeout();

        // Drop the skewed string list
        queryText = "delete from " + SKEWED_STRING_LIST + " where \"STRING_LIST_ID\" in ("
            + skewedStringListIds + ")";
        executeNoResult(queryText);
        Deadline.checkTimeout();
      }

      // Drop the skewed cols
      queryText = "delete from " + SKEWED_COL_NAMES + " where \"SD_ID\" in (" + sdIds + ")";
      executeNoResult(queryText);
      Deadline.checkTimeout();

      // Drop the sds
      queryText = "delete from " + SDS + " where \"SD_ID\" in (" + sdIds + ")";
      executeNoResult(queryText);
    } catch (SQLException sqlException) {
      LOG.warn("SQL error executing query while dropping storage descriptor.", sqlException);
      throw new MetaException("Encountered error while dropping storage descriptor.");
    }
  }

};